package com.example.content_provider

/**
 * This class contains constants used in the content provider and other parts of the app.
 */
class MyPreferences {
    companion object {
        // Key to save and retrieve an integer preference.
        const val SAVED_INT_KEY = "count"

        // Content URI for the content provider.
        const val URI = "content://com.example.content_provider.provider/"

        // Authority for the content provider.
        const val AUTHORITY = "com.example.content_provider.provider"

        // Method name to get an integer preference from the content provider.
        const val GET_INT_PREFERENCE_METHOD = "getStringPreference"

        // Method name to save an integer preference in the content provider.
        const val SAVE_PREFERENCE_METHOD = "savePreferences"
    }
}
