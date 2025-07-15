package com.mustafacan.feature.users.ui.onlineusers

import com.mustafacan.core.ui.component.dialog.DialogModel
import com.mustafacan.core.ui.model.UserUiModel

sealed class OnlineUsersUiEvent {
    object ConnectSocket: OnlineUsersUiEvent()
    data class ShowDialog(val dialogModel: DialogModel) : OnlineUsersUiEvent()
    object DismissDialog : OnlineUsersUiEvent()
    data class Search(val query: String): OnlineUsersUiEvent()
    data class NavigateToDirectMessage(val user: UserUiModel): OnlineUsersUiEvent()

}