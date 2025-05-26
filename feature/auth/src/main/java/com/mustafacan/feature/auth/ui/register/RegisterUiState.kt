package com.mustafacan.feature.auth.ui.register

import com.mustafacan.core.ui.component.dialog.DialogModel

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isRegisterButtonEnabled: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val dialogModel: DialogModel? = null
)