package com.mustafacan.feature.messages.ui.messages

import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.feature.messages.ui.messages.model.DirectMessageRoomUiModel

data class MessagesUiState(val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                           val userId: String = "",
                           val loading: Boolean = true,
                           val hasError: Boolean = false,
                           val messageRooms: List<DirectMessageRoomUiModel> = listOf(),
                           val typingRoomIds: List<String> = listOf(),
                           val hasUnreadWhileTabClosed: Boolean = false
)