package com.example.bunpolite.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunpolite.data.repository.LessonRepository
import com.example.bunpolite.data.repository.SettingsRepository
import com.example.bunpolite.domain.GetMainLessonsAndLanguageViewUC
import com.example.bunpolite.ui.shared.ShowSnackbar
import com.example.bunpolite.ui.shared.launchCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainLessonViewModel @Inject constructor(
    private val lessonRepository: LessonRepository,
    private val settingsRepository: SettingsRepository,
    getMainLessonsAndLanguageViewUC: GetMainLessonsAndLanguageViewUC
) : ViewModel() {

    val uiState: StateFlow<MainLessonUiState> = getMainLessonsAndLanguageViewUC()
        .catch { MainLessonUiState() }
        .map { (lessons, isMainLessonEnView) -> MainLessonUiState(lessons, isMainLessonEnView) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainLessonUiState()
        )

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    fun refresh(showSnackbar: ShowSnackbar) {
        _isRefreshing.update { true }
        launchCatching(showSnackbar) {
            lessonRepository.refreshMainLessons()
        }
        _isRefreshing.update { false }
    }

    fun toggleInfoLanguageView(showSnackbar: ShowSnackbar) {
        val toggledValue = !uiState.value.isMainLessonEnView
        launchCatching(showSnackbar) {
            settingsRepository.changeMainLessonView(toggledValue)
        }
    }
}