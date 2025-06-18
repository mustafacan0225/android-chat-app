package com.mustafacan.feature.users.ui.onlineusers

import com.mustafacan.core.domain.model.socket.OnlineUser
import com.mustafacan.core.domain.model.socket.SocketConnectionState
import com.mustafacan.core.ui.component.dialog.DialogModel

data class OnlineUsersUiState(val userId: String = "",
                              val dialogModel: DialogModel? = null,
                              val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                              val onlineUsers: List<OnlineUser> = emptyList(),
                              val searchedOnlineUsers: List<OnlineUser> = emptyList(),
                              val titleOnlineUsers: String = "",
                              val searchedText: String = "")