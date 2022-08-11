package smir.shitab14.localizationsample

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*

/**
 * Created by Shitab Mir on 08 August, 2022.
 * HungryNaki (Technology), Daraz Bangladesh Limited, Alibaba Group
 * mushfiq.islam@hungrynaki.com | shitabmir@gmail.com
 */
class LocaleManager(val context: Context) {
    private val LANGUAGE_KEY = "CURRENT_LANGUAGE"
    private val TAG = "LocaleManager"

    fun setLocale(c: Context): Context {
        val savedLanguage = getLanguage(c)
        Log.e(TAG, "Set Locale: $savedLanguage")
        return updateResources(c, savedLanguage)
    }

    fun setNewLocale(c: Context, language: String): Context {
        setLanguage(c, language)
        Log.e(TAG, "Set Locale: $language")
        return updateResources(c, language)
    }

    fun setNewLocale(c: Context, newLocale: Locale): Context {
        setLanguage(c, newLocale.toString())
        return updateResources(c, newLocale)
    }

    fun getLanguage(c: Context): String {
        val prefs = c.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val currentLocale = getLocale(c.resources)
        if (!prefs.contains(LANGUAGE_KEY)) {
            Log.e(TAG, "getLanguage: en")
            return "en"
        }
        else {
            val v = prefs.getString(LANGUAGE_KEY, currentLocale.toString()) ?: "en"
            Log.e(TAG, "getLanguage: $v")

            return v
        }
    }

    @SuppressLint("ApplySharedPref")
    fun setLanguage(c: Context, language: String?) {
        val prefs = c.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        Log.e(TAG, "setLanguage: $language")

        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    /*private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        return updateResources(context, locale)
    }*/

    private fun updateResources(theContext: Context, language: String): Context {
        var context = theContext
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        when {
            Utility.isAtLeastVersion(Build.VERSION_CODES.N) -> {
                setLocaleForApi24(config, locale)
                context = context.createConfigurationContext(config)
            }
            Utility.isAtLeastVersion(Build.VERSION_CODES.JELLY_BEAN_MR1) -> {
                config.setLocale(locale)
                context = context.createConfigurationContext(config)
            }
            else -> {
                config.locale = locale
                res.updateConfiguration(config, res.displayMetrics)
            }
        }
        Log.e(TAG, "UpdateResource: ${Locale.getDefault().language}")

        return context
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun setLocaleForApi24(config: Configuration, target: Locale) {
        val set: MutableSet<Locale> = LinkedHashSet()
        // bring the target locale to the front of the list
        set.add(target)
        val all = LocaleList.getDefault()
        for (i in 0 until all.size()) {
            // append other locales supported by the user
            set.add(all[i])
        }
        val locales = set.toTypedArray()
        config.setLocales(LocaleList(*locales))
    }

    private fun updateResources(theContext: Context, locale: Locale): Context {
        var context = theContext
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        when {
            Utility.isAtLeastVersion(Build.VERSION_CODES.N) -> {
                setLocaleForApi24(config, locale)
                context = context.createConfigurationContext(config)
            }
            Utility.isAtLeastVersion(Build.VERSION_CODES.JELLY_BEAN_MR1) -> {
                config.setLocale(locale)
                context = context.createConfigurationContext(config)
            }
            else -> {
                config.locale = locale
                res.updateConfiguration(config, res.displayMetrics)
            }
        }
        return context
    }

    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (Build.VERSION.SDK_INT >= 24) config.locales[0] else config.locale
    }

    fun getSavedLocale(c: Context): Locale {
        val savedLanguage = getLanguage(c)
        return Locale(getLanguage(c))
    }
}