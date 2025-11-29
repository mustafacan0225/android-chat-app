package com.mustafacan.feature.messages.ui.messages.model

import com.mustafacan.core.model.chat.UserRef
import com.mustafacan.core.model.room.LastMessage

data class DirectMessageRoomUiModel(
    val id: String,
    val type: String,
    val users: List<UserRef>,
    val lastMessage: LastMessage?,
    val createdAt: String,
    val updatedAt: String,
    val hasNewMessage: Boolean = false
)