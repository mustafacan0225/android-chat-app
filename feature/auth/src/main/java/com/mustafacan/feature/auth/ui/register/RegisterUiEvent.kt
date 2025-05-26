package com.mustafacan.feature.auth.ui.register

import com.mustafacan.core.ui.component.dialog.DialogModel

sealed class RegisterUiEvent {
    data class UserNameChanged(val username: String) : RegisterUiEvent()
    data class EmailChanged(val email: String) : RegisterUiEvent()
    data class PasswordChanged(val password: String) : RegisterUiEvent()
    object LoginClicked : RegisterUiEvent()
    object RegisterClicked : RegisterUiEvent()
    object TogglePasswordVisibility : RegisterUiEvent()
    data class ShowDialog(val dialogModel: DialogModel) : RegisterUiEvent()
    object DismissDialog : RegisterUiEvent()
}