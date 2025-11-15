package com.mustafacan.core.model.room

import com.mustafacan.core.model.chat.UserRef

data class LastMessage(
    val sender: UserRef?,
    val message: String?,
    val seenBy: List<UserRef>?,
    val createdAt: String?
)
