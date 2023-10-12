package com.example.content_provider

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class MySharedPreference(activity: Activity) {

    companion object {
        private val TAG = MySharedPreference::class.java.simpleName
        const val SAVED_INT_LOCAL_KEY = "count"
    }

    private var prefs: SharedPreferences

    init {
        prefs = activity.getSharedPreferences("savedPreferences", Context.MODE_PRIVATE)
    }

    fun savePreferences(value: Int) {
        with(prefs.edit()) {
            putInt(
                SAVED_INT_LOCAL_KEY,
                value
            )
            commit()
        }
    }

    fun readPreferences() {
        val intPreference = prefs.getInt(
            SAVED_INT_LOCAL_KEY,
            0
        )
        Log.d(TAG, "local int: {$intPreference}")
    }
}