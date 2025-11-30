package com.mustafacan.core.model.chat

data class TypingModel(val sender: String,
                       val receiver: String? = null,
                       val roomId: String? = null,
                       val username: String,
                       val channelType: String)
