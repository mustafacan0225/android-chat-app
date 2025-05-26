package com.mustafacan.feature.auth.ui.login

import com.mustafacan.core.ui.component.dialog.DialogModel

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginButtonEnabled: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val dialogModel: DialogModel? = null
)