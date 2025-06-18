package com.mustafacan.feature.users.ui.onlineusers

import com.mustafacan.core.ui.component.dialog.DialogModel

sealed class OnlineUsersUiEvent {
    object ConnectSocket: OnlineUsersUiEvent()
    data class ShowDialog(val dialogModel: DialogModel) : OnlineUsersUiEvent()
    object DismissDialog : OnlineUsersUiEvent()
    data class Search(val query: String): OnlineUsersUiEvent()
}