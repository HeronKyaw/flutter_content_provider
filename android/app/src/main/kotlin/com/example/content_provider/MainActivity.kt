package com.example.content_provider

import android.content.ContentValues
import android.database.Cursor
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private lateinit var countContentProvider: CountContentProvider
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        countContentProvider = CountContentProvider()

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "com.example.content_provider/CountContentProvider").setMethodCallHandler { call, result ->
            when (call.method) {
                "getCount" -> {
                    val key = call.arguments as? String
                    if (key != null) {
                        var cursor: Cursor? = null
                        try {
                            cursor = countContentProvider.query(
                                CountContentProvider.CONTENT_URI,
                                null,
                                key,
                                null,
                                null
                            )

                            if (cursor != null && cursor.moveToFirst()) {
                                val columnIndex = cursor.getColumnIndex(key)
                                result.success(cursor.getString(columnIndex))
                            } else {
                                result.success(null)
                            }
                        } catch (e: Exception) {
                            result.error(e.message ?: "", e.message ?: "", null)
                        } finally {
                            cursor?.close()
                        }
                    } else {
                        // Handle the case where key is null
                        result.error("Key is null", "Key cannot be null", null)
                    }
                }
                "setCount" -> {
                    try {
                        val values = ContentValues()
                        val arguments = call.arguments as Map<String, String>
                        arguments.keys.forEach {
                            values.put(it, arguments[it])
                        }

                        countContentProvider.insert(CountContentProvider.CONTENT_URI, values)
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
}
