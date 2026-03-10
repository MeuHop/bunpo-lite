package com.example.bunpolite.ui.shared

private const val SUCCESS = "Success"
private const val GENERIC_ERROR = "Something went wrong, try again"
sealed class SnackbarMessage(val message: String) {
    class GenericError : SnackbarMessage(GENERIC_ERROR)
    class DetailError(error: String) : SnackbarMessage(error)
    class Success : SnackbarMessage(SUCCESS)
}

typealias ShowSnackbar = (SnackbarMessage) -> Unit