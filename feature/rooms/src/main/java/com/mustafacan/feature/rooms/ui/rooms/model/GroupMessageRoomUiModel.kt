package com.mustafacan.feature.rooms.ui.rooms.model

import com.mustafacan.core.model.chat.UserRef
import com.mustafacan.core.model.room.LastMessage

data class GroupMessageRoomUiModel(
    val id: String,
    val type: String,
    val name: String,
    val description: String,
    val roomImage: String?,
    val users: List<UserRef>,
    val lastMessage: LastMessage?,
    val createdAt: String,
    val updatedAt: String,
    val hasNewMessage: Boolean = false
)