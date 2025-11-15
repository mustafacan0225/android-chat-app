package com.mustafacan.core.model.room

import com.mustafacan.core.model.chat.UserRef

data class DirectMessageRoomsResponseModel(
    val _id: String,
    val type: String,
    val users: List<UserRef>,
    val lastMessage: LastMessage?,
    val createdAt: String,
    val updatedAt: String
)