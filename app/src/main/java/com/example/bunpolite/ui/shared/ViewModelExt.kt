package com.example.bunpolite.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launchCatching(
    showSnackbar: ShowSnackbar = {},
    block: suspend CoroutineScope.() -> Unit
) =
    viewModelScope.launch(
        CoroutineExceptionHandler { _, throwable ->
            val error = if (throwable.message.isNullOrBlank()) {
                SnackbarMessage.GenericError()
            } else {
                SnackbarMessage.DetailError(throwable.message!!)
            }
            showSnackbar(error)
        }
    ) {
        block()
        showSnackbar(SnackbarMessage.Success())
    }