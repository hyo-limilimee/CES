package com.ssu.bilda.data.remote

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        var appContext: Context? = null
        lateinit var token_prefs: TokenSharedPreferences
        lateinit var globalNickname: String
    }

    override fun onCreate() {
        super.onCreate()
        token_prefs = TokenSharedPreferences(applicationContext)
        appContext = this

        globalNickname = ""
    }
}
