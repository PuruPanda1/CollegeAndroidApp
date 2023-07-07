package com.purabmodi.devhacksapp.utils

import android.content.Context

class SharedPref(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Devhacks", Context.MODE_PRIVATE)

    fun setUserName(usn: String) {
        val editor = sharedPreferences.edit()
        editor.putString("usn", usn)
        editor.apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString("usn", "ERROR")!!
    }

}