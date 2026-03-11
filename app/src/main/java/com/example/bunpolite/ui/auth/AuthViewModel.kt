package com.example.bunpolite.ui.auth

import androidx.lifecycle.ViewModel
import com.example.bunpolite.data.repository.AuthRepository
import com.example.bunpolite.ui.shared.ShowSnackbar
import com.example.bunpolite.ui.shared.launchCatching
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    fun signIn(
        showSnackbar: ShowSnackbar,
        email: String, password: String
    ) {
        launchCatching(showSnackbar) {
            authRepository.signIn(email, password)
            _shouldRestartApp.update { true }
        }
    }

    fun signUp(
        showSnackbar: ShowSnackbar,
        email: String, password: String
    ) {
        launchCatching(showSnackbar) {
            authRepository.signUp(email, password)
            _shouldRestartApp.update { true }
        }
    }
}