package com.example.bunpolite.ui.main

import com.example.bunpolite.data.model.MainLesson

data class MainLessonUiState(
    val mainLessons: List<MainLesson> = emptyList(),
    val isMainLessonEnView: Boolean = true,
    val currentSearchType: MainLessonFilterSearch = MainLessonFilterSearch.All,
    val isRefreshing: Boolean = false
) {
    val displayRefreshButton: Boolean
        get() = mainLessons.isEmpty()
}
