package com.mustafacan.feature.auth.ui.login

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    object LoginClicked : LoginUiEvent()
    object RegisterClicked : LoginUiEvent()
    object TogglePasswordVisibility : LoginUiEvent()
}