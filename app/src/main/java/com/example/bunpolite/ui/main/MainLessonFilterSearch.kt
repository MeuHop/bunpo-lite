package com.example.bunpolite.ui.main

import androidx.annotation.StringRes
import com.example.bunpolite.R

enum class MainLessonFilterSearch(
    @param:StringRes val labelResId: Int,
    @param:StringRes val exampleResId: List<Int>
) {
    All(R.string.all,
        listOf(R.string.ex_title, R.string.ex_category_en, R.string.ex_category_jp,
            R.string.ex_sentence_pattern_en, R.string.ex_sentence_pattern_jp)),
    Title(R.string.all, listOf(R.string.ex_title)),
    Category(R.string.all, listOf(R.string.ex_category_en, R.string.ex_category_jp)),
    SentencePattern(R.string.all, listOf(R.string.ex_sentence_pattern_en, R.string.ex_sentence_pattern_jp))
}