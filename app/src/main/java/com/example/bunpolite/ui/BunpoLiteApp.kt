package com.example.bunpolite.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bunpolite.data.model.ThemeType
import com.example.bunpolite.ui.theme.BunpoLiteTheme

@Composable
fun BunpoLiteApp() {
    val viewModel: MainViewModel = hiltViewModel<MainViewModel>()
    val appTheme by viewModel.themeFlow.collectAsStateWithLifecycle()

    val darkTheme = when (appTheme) {
        ThemeType.Dark -> true
        ThemeType.Light -> false
        else -> isSystemInDarkTheme()
    }

    BunpoLiteTheme(darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BunpoLiteNavGraph()
        }
    }
}