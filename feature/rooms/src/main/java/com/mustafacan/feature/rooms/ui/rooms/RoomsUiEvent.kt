package com.mustafacan.feature.rooms.ui.rooms

import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.feature.rooms.ui.rooms.model.GroupMessageRoomUiModel

sealed class RoomsUiEvent {
    object Retry: RoomsUiEvent()
    data class NavigateToGroupMessage(val user: UserUiModel, val room: GroupMessageRoomUiModel): RoomsUiEvent()
    data class SetHasNewMessage(val messageRoomUiModel: GroupMessageRoomUiModel, val hasNewMessage: Boolean): RoomsUiEvent()
    object ClearHasUnreadWhileTabClosed: RoomsUiEvent()
}