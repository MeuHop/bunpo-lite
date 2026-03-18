package com.example.bunpolite.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.bunpolite.ui.shared.ShowSnackbar
import kotlinx.serialization.Serializable

@Serializable
object ProfileRoute

@Composable
fun ProfileScreen(
    goToAuthScreen: () -> Unit,
    showSnackbar: ShowSnackbar,
    viewModel: ProfileViewModel = hiltViewModel<ProfileViewModel>()
) {

}

@Composable
private fun ProfileScreenContent(
    goToAuthScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Change Theme
    // go to Sign In Screen / Sign Out
    // Prompt to refresh data
}