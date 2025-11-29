package com.mustafacan.core.model.room

import com.mustafacan.core.model.chat.UserRef

data class GroupMessageRoomsResponseModel(
    val _id: String,
    val type: String,
    val name: String,
    val description: String,
    val roomImage: String?,
    val users: List<UserRef>,
    val lastMessage: LastMessage?,
    val createdAt: String,
    val updatedAt: String
)