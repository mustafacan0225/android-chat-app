package com.mustafacan.feature.users.ui.allusers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mustafacan.core.model.users.User
import com.mustafacan.core.ui.component.header.ListHeaderItem
import com.mustafacan.core.ui.component.searchbar.RemoteSearchBar
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.component.error.ErrorView
import com.mustafacan.core.ui.component.loading.MoreItemsLoading
import com.mustafacan.core.ui.component.loading.VerticalRectangleShimmer
import com.mustafacan.core.ui.component.notfound.NotFoundScreenForSearch
import com.mustafacan.feature.users.ui.common.UserItem

@Composable
fun AllUsersRoute(
    viewModel: AllUsersViewModel,
    navController: NavHostController,
    parentNavController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)
    val allUsers : LazyPagingItems<User> = viewModel.allUsersPagingDataFlow.collectAsLazyPagingItems()
    val allUsersLazyListState = rememberLazyListState()
    val searchedPagingData = viewModel.searchedUsersPagingDataFlow.collectAsStateWithLifecycle().value
    val searchedUsers = searchedPagingData?.collectAsLazyPagingItems()

    AllUsersScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) }, allUsers, searchedUsers, allUsersLazyListState)
}

@Composable
fun AllUsersScreen(
    uiState: AllUsersUiState,
    onEvent: (AllUsersUiEvent) -> Unit,
    allUsers : LazyPagingItems<User>,
    searchedUsers : LazyPagingItems<User>? = null,
    allUsersLazyListState: LazyListState
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        ListHeaderItem(stringResource(R.string.all_users), showAllText = false)

        Spacer(modifier = Modifier.height(8.dp))

        RemoteSearchBar(uiState.searchedText, onSearch = {
            onEvent(AllUsersUiEvent.Search)
        }, onValueChange = {
            onEvent(AllUsersUiEvent.SearchedTextChanged(it))
        })

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.searchedText.isEmpty()) {
            AllUsers(uiState, onEvent, allUsers, allUsersLazyListState)
        }

        if (searchedUsers != null){
            SearchedUsers(uiState, onEvent, searchedUsers)
        }

    }
}

@Composable
fun AllUsers(uiState: AllUsersUiState,
             onEvent: (AllUsersUiEvent) -> Unit,
             allUsers : LazyPagingItems<User>,
             allUsersLazyListState: LazyListState) {

    LaunchedEffect(allUsers.loadState) {
        onEvent(AllUsersUiEvent.UsersLoadStateChanged(allUsers))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoadingUsers) {
            VerticalRectangleShimmer()
        } else if (uiState.usersLoadingError != null) {
            ErrorView(message = stringResource(R.string.default_error),
                onRetry = {
                    onEvent(AllUsersUiEvent.RetryAllUsers(allUsers))
                })
        } else if (uiState.isUsersListEmpty) {
            // to do(optional)
        } else {
            LazyColumn(modifier = Modifier.weight(1f),
                state = allUsersLazyListState) {
                items(allUsers.itemCount) { index ->
                    val user = allUsers[index]
                    if (user != null) {
                        UserItem(user,
                            buttonClicked = {

                            }, isSelf = user.id.equals(uiState.userId))
                    }
                }
            }

        }

        MoreItemsLoading(uiState.isAppendingUsers)

        if (uiState.usersAppendError != null) {
            ErrorView(message = stringResource(R.string.default_error),
                onRetry = {
                    onEvent(AllUsersUiEvent.RetryAllUsers(allUsers))
                })
        }
    }

}

@Composable
fun SearchedUsers(uiState: AllUsersUiState,
                  onEvent: (AllUsersUiEvent) -> Unit,
                  searchedUsers : LazyPagingItems<User>? = null) {

    searchedUsers?.let {
        LaunchedEffect(searchedUsers.loadState) {
            onEvent(AllUsersUiEvent.SearchedUsersLoadStateChanged(searchedUsers))
        }

        Column(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoadingSearchedUsers) {
                VerticalRectangleShimmer()
            } else if (uiState.searchedUsersLoadingError != null) {
                ErrorView(message = stringResource(R.string.default_error),
                    onRetry = {
                        onEvent(AllUsersUiEvent.RetryAllUsers(searchedUsers))
                    })
            } else if (uiState.isSearchedUsersListEmpty && uiState.searchedText.isNotEmpty()) {
                NotFoundScreenForSearch()
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(searchedUsers.itemCount) { index ->
                        val user = searchedUsers[index]
                        if (user != null) {
                            UserItem(user,
                                buttonClicked = {

                                }, isSelf = user.id.equals(uiState.userId))
                        }
                    }
                }

            }

            MoreItemsLoading(uiState.isAppendingSearchedUsers)

            if (uiState.searchedUsersAppendError != null) {
                ErrorView(message = stringResource(R.string.default_error),
                    onRetry = {
                        onEvent(AllUsersUiEvent.RetrySearchedUsers(searchedUsers))
                    })
            }
        }
    }
}