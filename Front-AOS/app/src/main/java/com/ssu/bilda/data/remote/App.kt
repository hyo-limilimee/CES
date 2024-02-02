package com.ssu.bilda.data.remote

import android.app.Application
import com.ssu.bilda.data.enums.Department

class App : Application() {
    companion object {
        private lateinit var _tokenPrefs: TokenSharedPreferences
        lateinit var globalName: String
        lateinit var globalNickname: String
        lateinit var globalStId: String
        lateinit var globalDep: Department


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
        globalName=""
        globalNickname=""
        globalStId=""
        globalDep=Department.COMPUTER
    }
}



