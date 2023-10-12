package com.example.content_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log

/**
 * This is a custom Content Provider that allows reading and saving integer preferences.
 */
class CountContentProvider : ContentProvider() {
    private lateinit var prefs: SharedPreferences

    companion object {
        private val TAG = ContentProvider::class.java.simpleName
        const val SAVED_PREFERENCES = "savedPreferences"
    }

    override fun onCreate(): Boolean {
        return if (context != null) {
            // Initialize SharedPreferences for this Content Provider.
            prefs = context!!.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE)
            true
        } else {
            Log.d(TAG, "context is null")
            false
        }
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle {
        val bundle = Bundle()
        Log.d(TAG, "call: {$method}")
        when (method) {
            MyPreferences.GET_INT_PREFERENCE_METHOD -> {
                val int: Int = getIntPreference()
                Log.d(TAG, "getInt ContentProvider: {$int}")
                bundle.putInt(MyPreferences.SAVED_INT_KEY, int)
            }
            MyPreferences.SAVE_PREFERENCE_METHOD -> {
                Log.d(TAG, "save")
                savePreferences(arg?.toInt() ?: 0)
            }
        }
        return bundle
    }

    /**
     * Get an integer preference value from SharedPreferences.
     *
     * @return The integer value read from SharedPreferences. If not found, it defaults to 0.
     */
    private fun getIntPreference(): Int {
        return prefs.getInt(
            MyPreferences.SAVED_INT_KEY,
            0
        )
    }

    /**
     * Save an integer preference value to SharedPreferences.
     *
     * @param int The integer value to be saved.
     */
    private fun savePreferences(int: Int) {
        with(prefs.edit()) {
            putInt(
                MyPreferences.SAVED_INT_KEY,
                int
            )
            commit()
        }
    }

    /**
     * The following methods are not implemented.
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw NotImplementedError("delete is not implemented")
    }

    override fun getType(uri: Uri): String? {
        throw NotImplementedError("getType is not implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw NotImplementedError("insert is not implemented")
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        throw NotImplementedError("query is not implemented")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw NotImplementedError("update is not implemented")
    }
}