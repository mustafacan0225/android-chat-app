package com.mustafacan.feature.users.ui.home

import androidx.paging.compose.LazyPagingItems
import com.mustafacan.core.model.users.User
import com.mustafacan.core.ui.component.dialog.DialogModel
import com.mustafacan.core.ui.model.UserUiModel

sealed class HomeUiEvent {
    object ConnectSocket: HomeUiEvent()
    data class ShowDialog(val dialogModel: DialogModel) : HomeUiEvent()
    object DismissDialog : HomeUiEvent()
    data class AllUsersLoadStateChanged(val users: LazyPagingItems<User>) : HomeUiEvent()
    data class RetryAllUsers(val users: LazyPagingItems<User>) : HomeUiEvent()
    object NavigateToOnlineUsersPage: HomeUiEvent()
    object NavigateToAllUsersPage: HomeUiEvent()
    data class NavigateToDirectMessage(val user: UserUiModel): HomeUiEvent()
    data class SetTopAppBarVisibility(val visible: Boolean): HomeUiEvent()
    data class SetBottomBarVisibility(val visible: Boolean): HomeUiEvent()
}