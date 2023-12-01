package com.ssu.bilda.data.remote

import android.content.Context
import android.content.SharedPreferences
import com.ssu.bilda.data.enums.Department

object UserSharedPreferences {
    private val MY_ACCOUNT: String = "account"
    const val USER_NICKNAME = "USER_NICKNAME"
    const val USER_NAME = "USER_NAME"
    const val USER_STID = "USER_STID"
    const val USER_DEP = "USER_DEP"



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
        editor.commit()
    }

    fun getUserName(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString(USER_NAME, "").toString()
    }

    fun setUserStId(context: Context, input: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()

        editor.putString("USER_StId", input)
        editor.commit()
    }

    fun getUserStId(context: Context): String {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("USER_StId", "").toString()
    }

    fun setUserDep(context: Context, input: Department) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()

        val inputString = input.name // Enum 값을 String으로 변환

        editor.putString("USER_DEP", inputString)
        editor.apply()
    }

    fun getUserDep(context: Context): Department? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)

        val inputString = prefs.getString("USER_DEP", null)
        return inputString?.let { enumValueOf<Department>(it) } // String 값을 Enum으로 변환하여 반환
    }





    fun clearUser(context: Context) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.commit()
    }
}