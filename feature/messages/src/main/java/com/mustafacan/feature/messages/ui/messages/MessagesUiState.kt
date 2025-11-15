package com.mustafacan.feature.messages.ui.messages

import com.mustafacan.core.model.socket.SocketConnectionState

data class MessagesUiState(val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                           val userId: String = "")