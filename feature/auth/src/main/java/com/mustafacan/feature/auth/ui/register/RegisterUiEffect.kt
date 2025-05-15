package com.mustafacan.feature.auth.ui.register

sealed class RegisterUiEffect {
    object NavigateToLogin : RegisterUiEffect()
    object NavigateToHome : RegisterUiEffect()
}