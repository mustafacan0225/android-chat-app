package com.mustafacan.feature.chat.ui.groupmessage

import androidx.paging.compose.LazyPagingItems
import com.mustafacan.core.model.chat.Message

sealed class GroupMessageUiEvent {
    data class MessagesLoadStateChanged(val messages: LazyPagingItems<Message>) : GroupMessageUiEvent()
    object SendMessage: GroupMessageUiEvent()
    data class MessageValueChanged(val message: String): GroupMessageUiEvent()
}