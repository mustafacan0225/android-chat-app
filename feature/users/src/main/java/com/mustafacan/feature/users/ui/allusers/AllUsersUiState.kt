package com.mustafacan.feature.users.ui.allusers

import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.ui.component.dialog.DialogModel

data class AllUsersUiState(val userId: String = "",
                           val dialogModel: DialogModel? = null,
                           val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
                           val searchedText: String = "",
                           val isLoadingUsers: Boolean = false,
                           val isAppendingUsers: Boolean = false,
                           val usersLoadingError: String? = null,
                           val usersAppendError: String? = null,
                           val isUsersListEmpty: Boolean = false,
                           val isLoadingSearchedUsers: Boolean = false,
                           val isAppendingSearchedUsers: Boolean = false,
                           val searchedUsersLoadingError: String? = null,
                           val searchedUsersAppendError: String? = null,
                           val isSearchedUsersListEmpty: Boolean = false,)