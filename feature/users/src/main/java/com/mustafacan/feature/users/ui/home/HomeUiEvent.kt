package com.mustafacan.feature.users.ui.home

import androidx.paging.compose.LazyPagingItems
import com.mustafacan.core.model.users.User
import com.mustafacan.core.ui.component.dialog.DialogModel

sealed class HomeUiEvent {
    object ConnectSocket: HomeUiEvent()
    data class ShowDialog(val dialogModel: DialogModel) : HomeUiEvent()
    object DismissDialog : HomeUiEvent()
    data class AllUsersLoadStateChanged(val users: LazyPagingItems<User>) : HomeUiEvent()
    data class RetryAllUsers(val users: LazyPagingItems<User>) : HomeUiEvent()
    object NavigateToOnlineUsersPage: HomeUiEvent()
    object NavigateToAllUsersPage: HomeUiEvent()
}