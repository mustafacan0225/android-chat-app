package com.mustafacan.core.model.chat

data class Message(
    val _id: String,
    val chatRoom: String,
    val type: String,
    val sender: UserRef,
    val receiver: UserRef? = null,
    val message: String,
    val seenBy: List<UserRef>,
    val createdAt: String
)