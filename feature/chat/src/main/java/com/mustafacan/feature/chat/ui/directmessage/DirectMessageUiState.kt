package com.mustafacan.feature.chat.ui.directmessage

import androidx.compose.ui.graphics.Color
import com.mustafacan.core.model.chat.Message
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.ui.model.UserUiModel

data class DirectMessageUiState(val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                                val userId: String = "",
                                val receiverUser: UserUiModel? = null,
                                val receiverUserStatus: String = "",
                                val receiverUserStatusColor: Color = Color.Gray,
                                val previousPage: String = "",
                                val initialProgressVisibility: Boolean = true,
                                val isLoadingMessages: Boolean = false,
                                val isPrependingMessages: Boolean = false,
                                val messagesLoadingError: String? = null,
                                val messagesPrependError: String? = null,
                                val isMessageListEmpty: Boolean = false,
                                val socketMessages: List<Message> = listOf(),
                                val messageValue: String = "",
                                val previousFirstVisibleItemIndex: Int = 0,
                                val currentFirstVisibleItemIndex: Int = 0,
                                val previousFirstVisibleItemOffset: Int = 0,
                                val isPrependLoading: Boolean = false,
                                val isFirstLoadingCompleted: Boolean = false,
                                val showTyping: Boolean = false,

)
