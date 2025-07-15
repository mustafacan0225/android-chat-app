package com.mustafacan.feature.users.ui.home

import com.mustafacan.core.ui.model.UserUiModel

sealed class HomeUiEffect {
    object NavigateToMessage : HomeUiEffect()
    object NavigateToOnlineUsersPage : HomeUiEffect()
    object NavigateToAllUsersPage : HomeUiEffect()
    data class NavigateToDirectMessage(val user: UserUiModel): HomeUiEffect()

}