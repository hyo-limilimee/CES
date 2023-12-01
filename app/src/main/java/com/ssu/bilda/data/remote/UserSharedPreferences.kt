package com.ssu.bilda.data.remote

import android.content.Context
import android.content.SharedPreferences

object UserSharedPreferences {
    private val MY_ACCOUNT: String = "account"
    const val USER_PW_TO_STAR = "USER_PW_TO_STAR"
    const val USER_NICKNAME = "USER_NICKNAME"
    const val USER_PLATFORM = "USER_PLATFORM"
    const val USER_NAME = "USER_NAME"

    fun setUserEmail(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()

        editor.putString("USER_EMAIL", input)
        editor.commit()
    }

    fun getUserEmail(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("USER_EMAIL", "").toString()
    }

    fun setUserPw(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()

        editor.putString("USER_PW", input)
        editor.commit()
    }

    fun getUserPw(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("USER_PW", "").toString()
    }

    fun setUserNickname(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(USER_NICKNAME, input)
        editor.commit()
    }

    fun getUserNickname(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString(USER_NICKNAME, "").toString()
    }

    fun setUserName(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(USER_NAME, input)
        editor.apply()
    }

    fun getUserName(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString(USER_NAME, "").toString()
    }

    fun clearUser(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }
}