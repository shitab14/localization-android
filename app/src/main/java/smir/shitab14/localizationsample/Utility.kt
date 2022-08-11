package smir.shitab14.localizationsample

import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES
import android.util.Log
import java.lang.reflect.Method

/**
 * Created by Shitab Mir on 08 August, 2022.
 * HungryNaki (Technology), Daraz Bangladesh Limited, Alibaba Group
 * mushfiq.islam@hungrynaki.com | shitabmir@gmail.com
 */
object Utility {
    fun hexString(res: Resources?): String {
        val resImpl = getPrivateField("android.content.res.Resources", "mResourcesImpl", res)
        val o = (resImpl ?: res)!!
        return "@" + Integer.toHexString(o.hashCode())
    }

    fun getPrivateField(className: String?, fieldName: String?, `object`: Any?): Any? {
        return try {
            val c = Class.forName(className)
            val f = c.getDeclaredField(fieldName)
            f.isAccessible = true
            f[`object`]
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    @JvmStatic
    fun bypassHiddenApiRestrictions() {
        // http://weishu.me/2019/03/16/another-free-reflection-above-android-p/
        if (!isAtLeastVersion(VERSION_CODES.P)) return
        try {
            val forName =
                Class::class.java.getDeclaredMethod("forName", String::class.java)
            val getDeclaredMethod = Class::class.java.getDeclaredMethod(
                "getDeclaredMethod",
                String::class.java, Array<Any>::class.java
            )
            val vmRuntimeClass = forName.invoke(null, "dalvik.system.VMRuntime") as Class<*>
            val getRuntime = getDeclaredMethod.invoke(
                vmRuntimeClass, "getRuntime",
                null
            ) as Method
            val setHiddenApiExemptions = getDeclaredMethod.invoke(
                vmRuntimeClass,
                "setHiddenApiExemptions", arrayOf<Class<*>>(Array<String>::class.java)
            ) as Method
            val sVmRuntime = getRuntime.invoke(null)
            setHiddenApiExemptions.invoke(sVmRuntime, *arrayOf<Any>(arrayOf("L")))
        } catch (e: Throwable) {
//            Log.e(TAG, "Reflect bootstrap failed:", e)
        }
    }

    fun resetActivityTitle(a: Activity) {
        try {
            val info =
                a.packageManager.getActivityInfo(a.componentName, PackageManager.GET_META_DATA)
            if (info.labelRes != 0) {
                a.setTitle(info.labelRes)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun getTopLevelResources(a: Activity): Resources {
        return try {
            a.packageManager.getResourcesForApplication(a.applicationInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(e)
        }
    }

    @JvmStatic
    fun isAtLeastVersion(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }
}