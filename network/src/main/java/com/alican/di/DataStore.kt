package com.alican.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

const val PREFERENCES_NAME = "my_preferences"
val EXAMPLE_KEY = stringPreferencesKey("example_key")

class DataStoreHelper(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
    private var cachedToken: String? = null

    suspend fun writeValue(name: Preferences.Key<String> = EXAMPLE_KEY, value: String) {
        context.dataStore.edit { preferences ->
            preferences[name] = value
        }
        cachedToken = value
    }

    suspend fun readValue(name: Preferences.Key<String> = EXAMPLE_KEY): String? {
        if (cachedToken == null) {
            cachedToken = context.dataStore.data
                .map { preferences -> preferences[name] }
                .firstOrNull()
        }
        return cachedToken
    }
}

