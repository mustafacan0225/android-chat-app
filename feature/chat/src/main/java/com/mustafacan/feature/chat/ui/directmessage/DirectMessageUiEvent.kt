package com.mustafacan.feature.chat.ui.directmessage

import androidx.paging.compose.LazyPagingItems
import com.mustafacan.core.model.chat.Message

sealed class DirectMessageUiEvent {
    data class MessagesLoadStateChanged(val messages: LazyPagingItems<Message>) : DirectMessageUiEvent()
    object SendMessage: DirectMessageUiEvent()
    data class MessageValueChanged(val message: String): DirectMessageUiEvent()

}
