package com.example.bunpolite.domain

import com.example.bunpolite.data.model.MainLesson
import com.example.bunpolite.data.repository.LessonRepository
import com.example.bunpolite.data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetMainLessonsAndLanguageViewUC @Inject constructor(
    private val lessonRepository: LessonRepository,
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Pair<List<MainLesson>, Boolean>> {
        return combine(
            lessonRepository.getMainLessons(),
            settingsRepository.isMainLessonEnView
        ) { lessons, isMainLessonEnView -> lessons to isMainLessonEnView }
    }
}