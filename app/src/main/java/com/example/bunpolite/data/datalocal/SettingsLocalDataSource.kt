package com.example.bunpolite.data.datalocal

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val isMainLessonEnView: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { prefs -> prefs[MAIN_LESSON_EN_VIEW] ?: true }

    suspend fun changeMainLessonView(isMainLessonEnView: Boolean) {
        dataStore.edit { prefs ->
            prefs[MAIN_LESSON_EN_VIEW] = isMainLessonEnView
        }
    }

    val themeValueFlow: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { prefs -> prefs[THEME_KEY] ?: 0 }

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