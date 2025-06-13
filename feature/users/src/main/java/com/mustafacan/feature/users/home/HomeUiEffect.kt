package com.mustafacan.feature.users.home

sealed class HomeUiEffect {
    object NavigateToMessage : HomeUiEffect()
    object NavigateToOnlineUsersPage : HomeUiEffect()
    object NavigateToAllUsersPage : HomeUiEffect()
}