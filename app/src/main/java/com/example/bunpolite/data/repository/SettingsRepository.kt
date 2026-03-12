package com.example.bunpolite.data.repository

import com.example.bunpolite.data.datalocal.SettingsLocalDataSource
import com.example.bunpolite.data.model.ThemeType
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val settingsLocalDataSource: SettingsLocalDataSource
) {
    val isMainLessonEnView = settingsLocalDataSource.isMainLessonEnView
    val themeFlow = settingsLocalDataSource.themeValueFlow
        .map { themeValue ->
            when (themeValue) {
                ThemeType.Light.typeValue -> ThemeType.Light
                ThemeType.Dark.typeValue -> ThemeType.Dark
                else -> ThemeType.System
            }
        }

    suspend fun changeMainLessonView(isMainLessonEnView: Boolean) {
        settingsLocalDataSource.changeMainLessonView(isMainLessonEnView)
    }

    suspend fun saveThemeOption(theme: ThemeType) {
        settingsLocalDataSource.saveThemeOption(theme.typeValue)
    }
}