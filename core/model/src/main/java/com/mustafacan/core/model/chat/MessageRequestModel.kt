package com.mustafacan.core.model.chat

data class MessageRequestModel(
    val type: String,
    val sender: String,
    val receiver: String,
    val message: String,
    val roomId: String? = null
)