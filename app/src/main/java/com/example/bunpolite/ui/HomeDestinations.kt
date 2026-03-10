package com.example.bunpolite.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bunpolite.R

enum class HomeDestinations(
    @param:StringRes val labelResId: Int,
    val icon: ImageVector,
) {
    Main(R.string.main, Icons.Default.Home),
    Test(R.string.test, Icons.Default.Star),
    Profile(R.string.profile, Icons.Default.AccountBox),
}