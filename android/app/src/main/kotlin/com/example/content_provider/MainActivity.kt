package com.example.content_provider

import android.net.Uri
import android.os.Build
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val prefs by lazy {
        LocalSharedPreference(this)
    }

    companion object {
        // Define the Content Provider URI and authority.
        val uri: Uri = Uri.parse(MyPreferences.URI)
        const val auth = MyPreferences.AUTHORITY
    }

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "com.example.content_provider.CountContentProvider")
            .setMethodCallHandler { call, result ->
                when (call.method) {
                    "getCount" -> {
                        result.success(readData().toString())
                    }
                    "setCount" -> {
                        try {
                            val arguments = call.arguments as Map<String, String>
                            arguments.keys.forEach {
                                prefs.savePreferences(arguments[it]?.toInt() ?: 0)
                                writeData(arguments[it]?.toInt() ?: 0)
                            }
                            prefs.readPreferences()
                            result.success(null)
                        } catch (e: Exception) {
                            result.error(e.message ?: "", e.message ?: "", null)
                        }
                    }
                    else -> {
                        result.notImplemented()
                    }
                }
            }
    }

    /**
     * Write an integer to the Content Provider.
     *
     * @param int The integer value to write to the Content Provider.
     */
    private fun writeData(int: Int) {
        val cr = contentResolver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            cr.call(auth, MyPreferences.SAVE_PREFERENCE_METHOD, int.toString(), null)
        } else {
            cr.call(uri, MyPreferences.SAVE_PREFERENCE_METHOD, int.toString(), null)
        }
    }

    /**
     * Read an integer from the Content Provider.
     *
     * @return The integer value read from the Content Provider. If not found, it defaults to null.
     */
    private fun readData(): Int? {
        val cr = contentResolver
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val bundle = cr.call(auth, MyPreferences.GET_INT_PREFERENCE_METHOD, null, null)
            bundle?.getInt(MyPreferences.SAVED_INT_KEY)
        } else {
            val bundle = cr.call(uri, MyPreferences.GET_INT_PREFERENCE_METHOD, null, null)
            bundle?.getInt(MyPreferences.SAVED_INT_KEY)
        }
    }

}
