package com.mustafacan.feature.messages.ui.messages

import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.feature.messages.ui.messages.model.DirectMessageRoomUiModel

sealed class MessagesUiEvent {
    object Retry: MessagesUiEvent()
    data class NavigateToDirectMessage(val user: UserUiModel): MessagesUiEvent()
    data class SetHasNewMessage(val messageRoomUiModel: DirectMessageRoomUiModel, val hasNewMessage: Boolean): MessagesUiEvent()
    object ClearHasUnreadWhileTabClosed: MessagesUiEvent()
}