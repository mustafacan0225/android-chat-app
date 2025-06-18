package com.mustafacan.feature.users.ui.home

sealed class HomeUiEffect {
    object NavigateToMessage : HomeUiEffect()
    object NavigateToOnlineUsersPage : HomeUiEffect()
    object NavigateToAllUsersPage : HomeUiEffect()
}