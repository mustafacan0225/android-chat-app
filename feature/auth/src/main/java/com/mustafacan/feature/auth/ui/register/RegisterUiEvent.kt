package com.mustafacan.feature.auth.ui.register

sealed class RegisterUiEvent {
    data class UserNameChanged(val username: String) : RegisterUiEvent()
    data class EmailChanged(val email: String) : RegisterUiEvent()
    data class PasswordChanged(val password: String) : RegisterUiEvent()
    object LoginClicked : RegisterUiEvent()
    object RegisterClicked : RegisterUiEvent()
    object TogglePasswordVisibility : RegisterUiEvent()
}