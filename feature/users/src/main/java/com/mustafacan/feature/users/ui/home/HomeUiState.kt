package com.mustafacan.feature.users.ui.home

import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.model.users.User
import com.mustafacan.core.ui.component.dialog.DialogModel

data class HomeUiState(
    val userId: String = "",
    val dialogModel: DialogModel? = null,
    val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
    val onlineUsers: List<User> = emptyList(),
    val isLoadingAllUsers: Boolean = false,
    val isAppendingAllUsers: Boolean = false,
    val allUsersLoadingError: String? = null,
    val allUsersAppendError: String? = null,
    val isAllUsersListEmpty: Boolean = false,
    val titleOnlineUsers: String = ""
)