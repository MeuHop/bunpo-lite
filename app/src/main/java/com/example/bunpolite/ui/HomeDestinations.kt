package com.example.bunpolite.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.bunpolite.R
import com.example.bunpolite.ui.main.MainLessonRoute
import com.example.bunpolite.ui.profile.ProfileRoute
import com.example.bunpolite.ui.test.TestRoute
import kotlinx.serialization.descriptors.StructureKind

enum class HomeDestinations(
    @param:StringRes val labelResId: Int,
    val icon: ImageVector,
    val route: Any
) {
    Main(R.string.main, Icons.Default.Home, MainLessonRoute),
    Test(R.string.test, Icons.Default.Star, TestRoute),
    Profile(R.string.profile, Icons.Default.AccountBox, ProfileRoute),
}