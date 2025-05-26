package com.mustafacan.feature.auth.ui.login

import com.mustafacan.core.ui.component.dialog.DialogModel

sealed class LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent()
    data class PasswordChanged(val password: String) : LoginUiEvent()
    object LoginClicked : LoginUiEvent()
    object RegisterClicked : LoginUiEvent()
    object TogglePasswordVisibility : LoginUiEvent()
    data class ShowDialog(val dialogModel: DialogModel) : LoginUiEvent()
    object DismissDialog : LoginUiEvent()
}