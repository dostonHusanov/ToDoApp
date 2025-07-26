package com.doston.checklist


import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import java.util.*

object LocaleManager {

    private const val LANGUAGE_KEY = "app_language"
    private const val PREF_NAME = "locale_prefs"

    fun setLocale(context: Context, language: String): Context {
        saveLanguage(context, language)

        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }

    fun applyStoredLocale(context: Context): Context {
        val lang = getLanguage(context)
        return setLocale(context, lang)
    }

    fun saveLanguage(context: Context, language: String) {
        getPrefs(context).edit().putString(LANGUAGE_KEY, language).apply()
    }

    fun getLanguage(context: Context): String {
        return getPrefs(context).getString(LANGUAGE_KEY, "uz") ?: "uz"
    }

    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}
