package smir.shitab14.localizationsample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.util.*


class MainActivity : BaseActivity() {

    private val KEY_CURRENT_LANGUAGE = "CURRENT_LANGUAGE"

    private var languages: ArrayList<Pair<String,String>> = arrayListOf(
        Pair(first = "default", second = "en"),
        Pair(first = "Bengali", second = "bn"),
        Pair(first = "Bengali - India", second = "bn-rIN"), // Doesn't Work Yet
        Pair(first = "Español", second = "es"),
    )
    lateinit var spinnerLanguage: Spinner

    var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupSpinner(languages)
    }

    private fun setupUI() {
        spinnerLanguage = findViewById(R.id.spinnerLanguage)
    }

    private fun setupSpinner(languages: ArrayList<Pair<String,String>>) {
        val languageList = mutableListOf<String>()
        for (item in languages) {
            languageList.add(item.first)
        }
        val adapter: ArrayAdapter<Any> = ArrayAdapter<Any>(this, android.R.layout.simple_spinner_item, languageList.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguage.adapter = adapter

        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerLanguage.setSelection(0,true)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0) {
                    spinnerLanguage.setSelection(position, true)

//                    myEdit.putString(KEY_CURRENT_LANGUAGE, languages[position].second)

//                    LocaleManager(context).setLanguage(context, languages[position].second)
                    Log.e("MainActivity", languages[position].second)
                    App.localeManager.setNewLocale(context, languages[position].second)

//                    refreshThisActivity()
                    goToNextActivity()

                }
            }

        }

    }

    private fun refreshThisActivity() {
        intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        finish()
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

    }

    private fun goToNextActivity() {
        val i = Intent(this, ContentActivity::class.java)
        startActivity(i)
//        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

    }

    /*override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ContextWrapper(newBase?.setAppLocale(LocaleManager(context).getLanguage(newBase))))
    }*/

    /*override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager(this).setNewLocale(this, LocaleManager(this).getLanguage(this))
    }*/

    private fun Context.setAppLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    }

    /*private fun Context.getLanguage(): String {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_CURRENT_LANGUAGE, null) ?: "en"
    }

    fun setLanguage(languageCode: String) {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_CURRENT_LANGUAGE, languageCode).apply()
    }*/
}