package com.example.testf.prefrence

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class ApplicationPrefrence: Application() {

    override fun onCreate() {
        super.onCreate()
        val theme = ProvideTheme(this).getThemeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(theme)

    }

}