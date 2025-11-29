package com.mustafacan.feature.messages.ui.messages.mapper

import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import com.mustafacan.feature.messages.ui.messages.model.DirectMessageRoomUiModel

fun DirectMessageRoomsResponseModel.toUiModel(hasNewMessage: Boolean = false): DirectMessageRoomUiModel {

    return DirectMessageRoomUiModel(
        id = _id,
        type = type,
        users = users,
        lastMessage = lastMessage,
        createdAt = lastMessage?.createdAt?: "",
        updatedAt = updatedAt,
        hasNewMessage = hasNewMessage
    )
}