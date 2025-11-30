package com.mustafacan.feature.rooms.ui.rooms

import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.feature.rooms.ui.rooms.model.GroupMessageRoomUiModel

data class RoomsUiState(val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                        val userId: String = "",
                        val loading: Boolean = true,
                        val hasError: Boolean = false,
                        val messageRooms: List<GroupMessageRoomUiModel> = listOf(),
                        val typingRoomIds: List<String> = listOf(),
                        val hasUnreadWhileTabClosed: Boolean = false
)
