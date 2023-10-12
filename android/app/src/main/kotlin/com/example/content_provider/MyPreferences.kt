package com.example.content_provider

class MyPreferences {
    companion object {
        const val SAVED_INT_KEY = "count"
        const val URI = "content://com.example.content_provider.provider/"
        const val AUTHORITY = "com.example.content_provider.provider"

        const val GET_INT_PREFERENCE_METHOD = "getStringPreference"
        const val SAVE_PREFERENCE_METHOD = "savePreferences"
        const val READ_PREFERENCE_METHOD = "readPreferences"
    }
}