package com.example.bunpolite.data.datalocal

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import javax.inject.Inject

class SettingsLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveThemeOption(themeValue: Int) {
        dataStore.edit { prefs ->
            prefs[THEME_KEY] = themeValue
        }
    }
    private companion object {
        val THEME_KEY = intPreferencesKey("app_theme")
    }
}