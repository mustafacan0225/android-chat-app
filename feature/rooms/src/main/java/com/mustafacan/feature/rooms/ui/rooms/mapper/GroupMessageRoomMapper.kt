package com.mustafacan.feature.rooms.ui.rooms.mapper

import com.mustafacan.core.model.room.GroupMessageRoomsResponseModel
import com.mustafacan.feature.rooms.ui.rooms.model.GroupMessageRoomUiModel

fun GroupMessageRoomsResponseModel.toUiModel(hasNewMessage: Boolean = false): GroupMessageRoomUiModel {

    return GroupMessageRoomUiModel(
        id = _id,
        type = type,
        name = name,
        description = description,
        roomImage = roomImage,
        users = users,
        lastMessage = lastMessage,
        createdAt = lastMessage?.createdAt?: "",
        updatedAt = updatedAt,
        hasNewMessage = hasNewMessage
    )
}