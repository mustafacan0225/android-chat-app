package com.mustafacan.feature.rooms.ui.rooms

import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.feature.rooms.ui.rooms.model.GroupMessageRoomUiModel

sealed class RoomsUiEffect {
    data class NavigateToGroupMessage(val user: UserUiModel, val room: GroupMessageRoomUiModel): RoomsUiEffect()
    object ScrollToTop: RoomsUiEffect()
}