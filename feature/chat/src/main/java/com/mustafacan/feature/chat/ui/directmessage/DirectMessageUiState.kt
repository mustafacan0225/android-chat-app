package com.mustafacan.feature.chat.ui.directmessage

import androidx.compose.ui.graphics.Color
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.ui.model.UserUiModel

data class DirectMessageUiState(val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                                val receiverUser: UserUiModel? = null,
                                val receiverUserStatus: String = "",
                                val receiverUserStatusColor: Color = Color.Gray,
                                val previousPage: String = "",
                                val initialProgressVisibility: Boolean = true,
)
