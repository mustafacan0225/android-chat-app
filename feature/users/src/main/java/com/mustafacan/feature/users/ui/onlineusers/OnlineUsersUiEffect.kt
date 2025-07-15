package com.mustafacan.feature.users.ui.onlineusers

import com.mustafacan.core.ui.model.UserUiModel


sealed class OnlineUsersUiEffect {
    data class NavigateToDirectMessage(val user: UserUiModel): OnlineUsersUiEffect()
}