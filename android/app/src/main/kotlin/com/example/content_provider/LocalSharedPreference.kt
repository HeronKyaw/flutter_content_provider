package com.example.content_provider

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * This class provides a convenient way to work with local SharedPreferences.
 * It allows you to save and read integer values from SharedPreferences.
 *
 * @param activity The activity that provides the context for SharedPreferences.
 */
class LocalSharedPreference(activity: Activity) {

    companion object {
        private val TAG = LocalSharedPreference::class.java.simpleName
        const val SAVED_INT_LOCAL_KEY = "count"
    }

    private var prefs: SharedPreferences

    init {
        // Initialize the SharedPreferences object with a custom name and mode.
        prefs = activity.getSharedPreferences("savedPreferences", Context.MODE_PRIVATE)
    }

    /**
     * Save an integer value to SharedPreferences.
     *
     * @param value The integer value to be saved.
     */
    fun savePreferences(value: Int) {
        with(prefs.edit()) {
            putInt(
                SAVED_INT_LOCAL_KEY,
                value
            )
            commit()
        }
    }

    /**
     * Read an integer value from SharedPreferences.
     *
     * @return The integer value read from SharedPreferences. If not found, it defaults to 0.
     */
    fun readPreferences(): Int {
        val intPreference = prefs.getInt(
            SAVED_INT_LOCAL_KEY,
            0
        )
        Log.d(TAG, "local int: {$intPreference}")
        return intPreference
    }
}
