package com.example.bunpolite.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunpolite.data.repository.LessonRepository
import com.example.bunpolite.data.repository.SettingsRepository
import com.example.bunpolite.domain.GetMainLessonsAndLanguageViewUC
import com.example.bunpolite.ui.shared.ShowSnackbar
import com.example.bunpolite.ui.shared.SnackbarMessage
import com.example.bunpolite.ui.shared.launchCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainLessonViewModel @Inject constructor(
    private val lessonRepository: LessonRepository,
    private val settingsRepository: SettingsRepository,
    getMainLessonsAndLanguageViewUC: GetMainLessonsAndLanguageViewUC
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    private val _currentSearchType = MutableStateFlow(MainLessonFilterSearch.All)

    val uiState: StateFlow<MainLessonUiState> = combine(
        getMainLessonsAndLanguageViewUC(),
        _isRefreshing, _currentSearchType
    ) { (lessons, isMainLessonEnView), isRefreshing, currentSearchType ->
        MainLessonUiState(
            mainLessons = lessons,
            isMainLessonEnView = isMainLessonEnView,
            currentSearchType = currentSearchType,
            isRefreshing = isRefreshing
        )
    }.catch { MainLessonUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainLessonUiState()
        )

    fun refresh(showSnackbar: ShowSnackbar) {
        _isRefreshing.update { true }
        launchCatching(showSnackbar) {
            lessonRepository.refreshMainLessons()
        }
        _isRefreshing.update { false }
    }

    fun onChangeCurrentSearchType(newValue: MainLessonFilterSearch) {
        _currentSearchType.update { newValue }
    }

    /** TODO: Implement search filter */
    /** Current problem: find suitable data type (Flow, snapshot?)
     * to separate between original list and filtered list for display
     * */
    fun onFilterListSearch(searchText: String) {
        val currentSearchType = _currentSearchType.value
        val query = searchText.trim().lowercase()
        val currentList = uiState.value.mainLessons

        fun String.isMatch(): Boolean {
            val normalizedTarget = this.replace("[", " ")
                .replace("]", " ")
                .replace(Regex("\\s+"), " ") // Clean up extra spaces
                .lowercase()

            return normalizedTarget.contains(query)
        }

        val filteredList = currentList.filter { item ->
            when (currentSearchType) {
                MainLessonFilterSearch.Title -> item.title.isMatch()
                MainLessonFilterSearch.Category ->
                    item.categoryEn.isMatch() || item.categoryJp.isMatch()
                MainLessonFilterSearch.SentencePattern ->
                    item.sentencePatternEn.isMatch() || item.sentencePatternJp.isMatch()
                MainLessonFilterSearch.All ->
                    item.title.isMatch() || item.categoryEn.isMatch() ||
                            item.categoryJp.isMatch() || item.sentencePatternEn.isMatch() ||
                            item.sentencePatternJp.isMatch()
            }
        }
    }

    fun onClickGoToLesson(showSnackbar: ShowSnackbar, number: String, returnNumber: (String) -> Unit) {
        val lessonNumber = number.toIntOrNull()
        if (lessonNumber == null) {
            showSnackbar(SnackbarMessage.InvalidFormatError())
            return
        }
        returnNumber(numberToStringId(lessonNumber))
    }

    fun toggleInfoLanguageView(showSnackbar: ShowSnackbar) {
        val toggledValue = !uiState.value.isMainLessonEnView
        launchCatching(showSnackbar) {
            settingsRepository.changeMainLessonView(toggledValue)
        }
    }
}

private fun numberToStringId(number: Int): String {
    return if (number < 10) "0$number" else number.toString()
}