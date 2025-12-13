package com.mustafacan.feature.chat.ui.groupmessage

import androidx.compose.ui.graphics.Color
import com.mustafacan.core.model.chat.Message
import com.mustafacan.core.model.socket.SocketConnectionState

data class GroupMessageUiState(val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                                val userId: String = "",
                                val roomId: String = "",
                                val roomName: String = "",
                                val roomImage: String = "",
                                val roomDescription: String = "",
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
                               val userColorMap: Map<String, Color> = mapOf())