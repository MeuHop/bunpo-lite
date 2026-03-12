package com.example.bunpolite.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bunpolite.R

enum class ThemeType(
    @param:StringRes val nameStrId: Int,
    val icon: ImageVector,
    val typeValue: Int
) {
    System(R.string.system_theme, Icons.Outlined.CheckCircle, 0),
    Light(R.string.light_theme, Icons.Filled.Check, 1),
    Dark(R.string.dark_theme, Icons.Filled.CheckCircle, 2)

}