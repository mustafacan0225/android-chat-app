package com.mustafacan.feature.auth.ui.login

sealed class LoginUiEffect {
    object NavigateToRegister : LoginUiEffect()
    object NavigateToHome : LoginUiEffect()
}