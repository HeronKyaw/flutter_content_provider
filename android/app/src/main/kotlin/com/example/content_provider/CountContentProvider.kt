package com.example.content_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log

class CountContentProvider : ContentProvider() {

    companion object {
        private val TAG = ContentProvider::class.java.simpleName
        const val SAVED_PREFERENCES = "savedPreferences"
    }

    private lateinit var prefs: SharedPreferences

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        return if (context != null) {
            prefs = context!!.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE)
            true
        } else {
            Log.d(TAG, "context is null")
            false
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        TODO("Implement this to handle query requests from clients.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle {
        val bundle = Bundle()
        Log.d(TAG, "call: {$method}")
        when (method) {
            MyPreferences.GET_INT_PREFERENCE_METHOD -> {
                Log.d(TAG, "get")
                val int: Int = getIntPreference()
                Log.d(TAG, "getInt ContentProvider: {$int}")
                bundle.putInt(MyPreferences.SAVED_INT_KEY, int)
            }
            MyPreferences.SAVE_PREFERENCE_METHOD -> {
                Log.d(TAG, "save")
                savePreferences(arg?.toInt() ?: 0)
            }
            MyPreferences.READ_PREFERENCE_METHOD -> {
                Log.d(TAG, "read")
                readPreferences()
            }
        }

        return bundle
    }

    private fun getIntPreference(): Int {
        return prefs.getInt(
            MyPreferences.SAVED_INT_KEY,
            0
        )
    }

    private fun savePreferences(int: Int) {
        with(prefs.edit()) {
            putInt(
                MyPreferences.SAVED_INT_KEY,
                int
            )
            commit()
        }
    }

    private fun readPreferences() {
        val intPreference = prefs.getInt(
            MyPreferences.SAVED_INT_KEY,
            0
        )
        Log.d(TAG, "int: {$intPreference}")
    }
}