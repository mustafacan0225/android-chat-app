package com.mustafacan.feature.users.ui.onlineusers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mustafacan.core.domain.model.socket.OnlineUser
import com.mustafacan.core.domain.model.socket.SocketConnectionState
import com.mustafacan.core.ui.component.error.ErrorView
import com.mustafacan.core.ui.component.header.ListHeaderItem
import com.mustafacan.core.ui.component.loading.VerticalRectangleShimmer
import com.mustafacan.core.ui.component.notfound.NotFoundScreenForSearch
import com.mustafacan.core.ui.component.searchbar.SearchBar
import com.mustafacan.core.ui.R
import com.mustafacan.feature.users.ui.common.UserItemForOnlineUsers

@Composable
fun OnlineUsersRoute(
    viewModel: OnlineUsersViewModel,
    navController: NavHostController,
    parentNavController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    //val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)

    OnlineUsersScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) })
}

@Composable
fun OnlineUsersScreen(
    uiState: OnlineUsersUiState,
    onEvent: (OnlineUsersUiEvent) -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        ListHeaderItem(uiState.titleOnlineUsers, showAllText = false)

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState.socketConnectionState == SocketConnectionState.CONNECTED) {
            SearchBar(uiState.searchedText, onSearch = {
                onEvent(OnlineUsersUiEvent.Search(query = it))
            } )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.searchedText.isNullOrEmpty()) {
                UserList(uiState.onlineUsers, onEvent)
            } else if (uiState.searchedText.isNotBlank() && uiState.searchedOnlineUsers.size > 0) {
                UserList(uiState.searchedOnlineUsers, onEvent)
            }  else if (uiState.searchedText.isNotBlank() && uiState.searchedOnlineUsers.size == 0) {
                NotFoundScreenForSearch()
            }
        } else if (uiState.socketConnectionState == SocketConnectionState.CONNECTING) {
            VerticalRectangleShimmer()
        } else {
            ErrorView(message = stringResource(R.string.default_error),
                onRetry = {
                    onEvent(OnlineUsersUiEvent.ConnectSocket)
                })

        }


    }
}

@Composable
fun UserList(users: List<OnlineUser>, onEvent: (OnlineUsersUiEvent) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users) { user ->
            UserItemForOnlineUsers(user,
                buttonClicked = {

                })
        }

    }
}