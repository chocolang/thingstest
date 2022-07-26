package com.chocolang.android.chocoapp.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedValue(context: Context){
    private val sharedPref = "choco"
    private val sharedPreferences : SharedPreferences

    private val path = "path"

    init {
        sharedPreferences = context.getSharedPreferences(sharedPref, Activity.MODE_PRIVATE)
    }

    fun setPath(value: String?) {
        value?.let {
            sharedPreferences
                .edit()
                .putString(path, it)
                .apply()
        }
    }

    fun getPath(): String? =
        sharedPreferences.getString(path, "")
}