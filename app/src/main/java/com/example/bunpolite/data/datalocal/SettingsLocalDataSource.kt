package com.example.bunpolite.data.datalocal

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import javax.inject.Inject

class SettingsLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun changeMainLessonView(isMainLessonEnView: Boolean) {
        dataStore.edit { prefs ->
            prefs[MAIN_LESSON_EN_VIEW] = isMainLessonEnView
        }
    }

    suspend fun saveThemeOption(themeValue: Int) {
        dataStore.edit { prefs ->
            prefs[THEME_KEY] = themeValue
        }
    }
    private companion object {
        val MAIN_LESSON_EN_VIEW = booleanPreferencesKey("main_lesson_en_view")
        val THEME_KEY = intPreferencesKey("app_theme")
    }
}