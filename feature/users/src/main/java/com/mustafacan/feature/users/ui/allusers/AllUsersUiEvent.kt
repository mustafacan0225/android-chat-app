package com.mustafacan.feature.users.ui.allusers

import androidx.paging.compose.LazyPagingItems
import com.mustafacan.core.model.users.User
import com.mustafacan.core.ui.component.dialog.DialogModel

sealed class AllUsersUiEvent {
    data class ShowDialog(val dialogModel: DialogModel) : AllUsersUiEvent()
    object DismissDialog : AllUsersUiEvent()
    object Search: AllUsersUiEvent()
    data class SearchedTextChanged(val searchedText: String): AllUsersUiEvent()
    data class RetryAllUsers(val users: LazyPagingItems<User>) : AllUsersUiEvent()
    data class UsersLoadStateChanged(val users: LazyPagingItems<User>) : AllUsersUiEvent()
    data class SearchedUsersLoadStateChanged(val users: LazyPagingItems<User>) : AllUsersUiEvent()
    data class RetrySearchedUsers(val users: LazyPagingItems<User>) : AllUsersUiEvent()
}