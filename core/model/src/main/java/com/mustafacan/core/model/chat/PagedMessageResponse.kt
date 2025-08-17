package com.mustafacan.core.model.chat

data class PagedMessageResponse(
    val messages: List<Message>,
    val hasMore: Boolean,
    val lastId: String?
)
