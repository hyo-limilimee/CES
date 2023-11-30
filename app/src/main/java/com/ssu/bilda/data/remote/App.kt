package com.ssu.bilda.data.remote

import android.app.Application

class App : Application() {
    companion object {
        private lateinit var _tokenPrefs: TokenSharedPreferences

        val token_prefs: TokenSharedPreferences
            get() {
                if (!::_tokenPrefs.isInitialized) {
                    throw IllegalStateException("TokenPrefs should be initialized before using it")
                }
                return _tokenPrefs
            }
    }

    override fun onCreate() {
        super.onCreate()
        _tokenPrefs = TokenSharedPreferences.getInstance(applicationContext)
    }
}



