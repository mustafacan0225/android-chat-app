package com.mustafacan.core.model.chat

data class TypingModel(val sender: String,
                       val receiver: String,
                       val roomId: String? = null,
                       val username: String)
