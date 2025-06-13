package com.mustafacan.data.datastore.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferencesDataStoreManager(
    val context: Context,
    val moshi: Moshi
) {

    val Context.dataStore by preferencesDataStore(name = "chat_app_preferences")

    suspend fun <T> saveData(key: String, value: T, type: Class<T>) {
        val preferencesKey = stringPreferencesKey(key)
        try {
            val stringValue = when (value) {
                is String -> value
                is Int, is Boolean, is Float, is Long, is Double -> value.toString()
                else -> moshi.adapter(type).toJson(value)
            }
            context.dataStore.edit { prefs ->
                prefs[preferencesKey] = stringValue
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun <T> saveDataList(key: String, list: List<T>, type: Class<T>) {
        val preferencesKey = stringPreferencesKey(key)
        try {
            val listType = Types.newParameterizedType(List::class.java, type)
            val json = moshi.adapter<List<T>>(listType).toJson(list)
            context.dataStore.edit { prefs ->
                prefs[preferencesKey] = json
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inline fun <reified T> getDataFlow(key: String, type: Class<T>): Flow<T?> {
        val preferencesKey = stringPreferencesKey(key)
        val adapter = moshi.adapter(type)

        return context.dataStore.data
            .catch { e ->
                // IOException gibi durumları yakalayabiliriz
                e.printStackTrace()
                emit(emptyPreferences())
            }
            .map { prefs ->
                try {
                    prefs[preferencesKey]?.let {
                        parseStringToType(it, type, adapter)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
    }

    suspend inline fun <reified T> getData(key: String, type: Class<T>): T? {
        return try {
            getDataFlow(key, type).first()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun <T> getDataListFlow(key: String, type: Class<T>): Flow<List<T>?> {
        val preferencesKey = stringPreferencesKey(key)
        val listType = Types.newParameterizedType(List::class.java, type)
        val adapter = moshi.adapter<List<T>>(listType)

        return context.dataStore.data
            .catch { e ->
                e.printStackTrace()
                emit(emptyPreferences())
            }
            .map { prefs ->
                try {
                    prefs[preferencesKey]?.let {
                        adapter.fromJson(it)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
    }

    suspend fun <T> getDataList(key: String, type: Class<T>): List<T>? {
        return try {
            getDataListFlow(key, type).first()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun clearData(key: String) {
        val preferencesKey = stringPreferencesKey(key)
        try {
            context.dataStore.edit { prefs ->
                prefs.remove(preferencesKey)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inline fun <reified T> parseStringToType(value: String, type: Class<T>, adapter: com.squareup.moshi.JsonAdapter<T>): T? {
        return try {
            when (type) {
                String::class.java -> value as T
                Int::class.java -> value.toIntOrNull() as T?
                Boolean::class.java -> value.toBooleanStrictOrNull() as T?
                Float::class.java -> value.toFloatOrNull() as T?
                Long::class.java -> value.toLongOrNull() as T?
                Double::class.java -> value.toDoubleOrNull() as T?
                else -> adapter.fromJson(value)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}