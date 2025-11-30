package com.mustafacan.feature.rooms.ui.rooms

import com.mustafacan.core.ui.model.UserUiModel

sealed class RoomsUiEffect {
    data class NavigateToDirectMessage(val user: UserUiModel): RoomsUiEffect()
    object ScrollToTop: RoomsUiEffect()
}