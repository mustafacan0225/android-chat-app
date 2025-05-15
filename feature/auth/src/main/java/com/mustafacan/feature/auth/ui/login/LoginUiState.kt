package com.mustafacan.feature.auth.ui.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginButtonEnabled: Boolean = false,
    val isPasswordVisible: Boolean = false,
)