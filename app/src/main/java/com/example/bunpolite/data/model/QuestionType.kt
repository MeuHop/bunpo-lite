package com.example.bunpolite.data.model

import androidx.annotation.StringRes
import com.example.bunpolite.R

enum class QuestionType(val typeValue: Int, @param:StringRes val guideResId: Int) {
    MultipleChoice(0, R.string.multiple_choice_guide),
    FillInTheBlank(1, R.string.fill_blank_guide)
}