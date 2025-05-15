package com.mustafacan.feature.auth.ui.register

data class RegisterUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isRegisterButtonEnabled: Boolean = false,
    val isPasswordVisible: Boolean = false,
)