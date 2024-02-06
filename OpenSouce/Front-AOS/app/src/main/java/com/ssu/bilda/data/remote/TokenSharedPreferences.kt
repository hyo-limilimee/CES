package com.ssu.bilda.data.remote

import android.content.Context
import android.content.SharedPreferences

class TokenSharedPreferences private constructor(context: Context) {

    private val prefsFilename = "token_prefs"
    private val keyAccessToken = "accessToken"
    private val keyRefreshToken = "refreshToken"

    private val prefs: SharedPreferences =
        context.getSharedPreferences(prefsFilename, Context.MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString(keyAccessToken, null)
        set(value) = prefs.edit().putString(keyAccessToken, value).apply()

    var refreshToken: String?
        get() = prefs.getString(keyRefreshToken, null)
        set(value) = prefs.edit().putString(keyRefreshToken, value).apply()

    fun clearTokens() {
        prefs.edit().remove(keyAccessToken).remove(keyRefreshToken).apply()
    }

    companion object {
        private var instance: TokenSharedPreferences? = null

        fun getInstance(context: Context): TokenSharedPreferences {
            return instance ?: synchronized(this) {
                instance ?: TokenSharedPreferences(context).also { instance = it }
            }
        }
    }
}
