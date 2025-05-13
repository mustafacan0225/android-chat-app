package com.mustafacan.auth.ui.login

sealed class LoginUiEffect {
    object NavigateToRegister : LoginUiEffect()
    object NavigateToHome : LoginUiEffect()
    data class ShowSnackbar(val message: String) : LoginUiEffect()
}