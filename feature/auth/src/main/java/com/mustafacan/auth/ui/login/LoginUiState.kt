package com.mustafacan.auth.ui.login

data class LoginUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginButtonEnabled: Boolean = false
)