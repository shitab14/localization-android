package smir.shitab14.localizationsample

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import java.util.*

/**
 * Created by Shitab Mir on 08 August, 2022.
 * HungryNaki (Technology), Daraz Bangladesh Limited, Alibaba Group
 * mushfiq.islam@hungrynaki.com | shitabmir@gmail.com
 **/
abstract class BaseActivity: AppCompatActivity() {
    private val TAG = "BaseActivity"

    override fun attachBaseContext(newBase: Context) {
//        Log.e(TAG, "get Locale: ${App.localeManager.language}")
        super.attachBaseContext(App.localeManager.setLocale(newBase))
        Log.e(TAG, "attachBaseContext")
        Log.e(TAG, "get Locale: ${Locale.getDefault().language}")

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Utility.resetActivityTitle(this)
        Log.e(TAG, "onCreate")
    }
}