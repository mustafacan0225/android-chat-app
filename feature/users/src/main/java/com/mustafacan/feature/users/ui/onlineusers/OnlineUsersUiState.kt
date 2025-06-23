package com.mustafacan.feature.users.ui.onlineusers

import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.model.users.User
import com.mustafacan.core.ui.component.dialog.DialogModel

data class OnlineUsersUiState(val userId: String = "",
                              val dialogModel: DialogModel? = null,
                              val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                              val onlineUsers: List<User> = emptyList(),
                              val searchedOnlineUsers: List<User> = emptyList(),
                              val titleOnlineUsers: String = "",
                              val searchedText: String = "")