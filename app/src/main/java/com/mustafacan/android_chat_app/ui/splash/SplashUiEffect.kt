package com.mustafacan.android_chat_app.ui.splash

import com.mustafacan.feature.auth.ui.login.LoginUiEffect

sealed class SplashUiEffect {
    object NavigateToLogin : SplashUiEffect()
    object NavigateToHome : SplashUiEffect()
}