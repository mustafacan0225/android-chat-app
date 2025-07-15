package com.mustafacan.feature.users.ui.allusers

import com.mustafacan.core.ui.model.UserUiModel


sealed class AllUsersUiEffect {
    data class NavigateToDirectMessage(val user: UserUiModel): AllUsersUiEffect()
}