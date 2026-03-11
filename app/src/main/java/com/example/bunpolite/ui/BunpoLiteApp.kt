package com.example.bunpolite.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bunpolite.ui.theme.BunpoLiteTheme

@Composable
fun BunpoLiteApp() {
    val viewModel: MainViewModel = hiltViewModel<MainViewModel>()
    /** TODO: Add theme */

    BunpoLiteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BunpoLiteNavGraph()
        }
    }
}