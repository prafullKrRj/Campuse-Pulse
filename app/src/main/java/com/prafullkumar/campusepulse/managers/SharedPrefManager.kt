package com.prafullkumar.campusepulse.managers

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
    fun loggedInUserType(): String {
        return sharedPreferences.getString("userType", "none").toString()
    }
    fun setLoggedInUserType(userType: String) {
        val editor = sharedPreferences.edit()
        editor.putString("userType", userType)
        editor.apply()
    }
}