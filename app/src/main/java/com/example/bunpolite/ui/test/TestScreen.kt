package com.example.bunpolite.ui.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bunpolite.ui.shared.ShowSnackbar
import kotlinx.serialization.Serializable

@Serializable
object TestRoute

@Composable
fun TestScreen(
    showSnackbar: ShowSnackbar,
) {

}

@Composable
private fun TestScreenContent(modifier: Modifier = Modifier) {

}