package com.mustafacan.feature.users.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mustafacan.core.domain.model.socket.SocketConnectionState
import com.mustafacan.core.domain.model.users.User
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.component.error.ErrorView
import com.mustafacan.core.ui.component.header.ListHeaderItem
import com.mustafacan.core.ui.component.loading.HorizontalCircleShimmer
import com.mustafacan.core.ui.component.loading.MoreItemsLoading
import com.mustafacan.core.ui.component.loading.VerticalRectangleShimmer
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    navController: NavHostController,
    parentNavController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)
    val allUsers : LazyPagingItems<User> = viewModel.allUsersPagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                HomeUiEffect.NavigateToMessage -> {
                    //navController.navigate(NavDestinationItem.Message)
                }

                HomeUiEffect.NavigateToAllUsersPage -> {

                }

                HomeUiEffect.NavigateToOnlineUsersPage -> {

                }
            }
        }
    }

    HomeScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) }, allUsers)
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    allUsers : LazyPagingItems<User>
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OnlineUsers(uiState, onEvent)
        Spacer(modifier = Modifier.height(16.dp))
        AllUsers(uiState, onEvent, allUsers)
    }
}

@Composable
fun OnlineUsers(uiState: HomeUiState, onEvent: (HomeUiEvent) -> Unit) {

    ListHeaderItem(uiState.titleOnlineUsers,
        onClick = { onEvent(HomeUiEvent.NavigateToOnlineUsersPage) },
        visibilityAll = (uiState.onlineUsers.size > 0 && uiState.socketConnectionState == SocketConnectionState.CONNECTED))

    Spacer(modifier = Modifier.height(16.dp))

    if (uiState.socketConnectionState == SocketConnectionState.CONNECTING) {
        HorizontalCircleShimmer()
    } else if (uiState.socketConnectionState == SocketConnectionState.CONNECTED) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.onlineUsers) { user ->
                Card(
                    modifier = Modifier
                        .width(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.first().uppercase(),
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF4CAF50))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = stringResource(R.string.connection_state_online),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Button(
                            onClick = {
                                // to do
                            },
                            modifier = Modifier.height(32.dp),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp),
                        ) {
                            Text(
                                text = stringResource(R.string.chat),
                                style = TextStyle(fontSize = 12.sp),
                                color = Color.White,
                                maxLines = 1
                            )
                        }
                    }
                }
            }

        }
    } else {
        ErrorView(message = stringResource(R.string.default_error),
            onRetry = {
                onEvent(HomeUiEvent.ConnectSocket)
        })

    }

}

@Composable
fun AllUsers(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    allUsers: LazyPagingItems<User>

) {

    LaunchedEffect(allUsers.loadState) {
        onEvent(HomeUiEvent.AllUsersLoadStateChanged(allUsers))
    }

    Column(modifier = Modifier.fillMaxSize()) {

        ListHeaderItem(stringResource(R.string.all_users),
            onClick = { onEvent(HomeUiEvent.NavigateToOnlineUsersPage) },
            visibilityAll = (allUsers.itemCount > 0 && (uiState.allUsersAppendError == null && uiState.allUsersLoadingError == null)))

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isAllUsersLoading) {
            VerticalRectangleShimmer()
        } else if (uiState.allUsersLoadingError != null) {
            ErrorView(message = stringResource(R.string.default_error),
                onRetry = {
                    onEvent(HomeUiEvent.RetryAllUsers(allUsers))
                })
        } else if (uiState.isAllUsersListEmpty) {
            // to do(optional)
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(allUsers.itemCount) { index ->
                    val user = allUsers[index]
                    if (user != null) {
                        AllUserListItem(user)
                    }
                }
            }

        }

        MoreItemsLoading(uiState.isAllUsersAppending)

        if (uiState.allUsersAppendError != null) {
            ErrorView(message = stringResource(R.string.default_error),
                onRetry = {
                    onEvent(HomeUiEvent.RetryAllUsers(allUsers))
                })
        }

    }

}


@Composable
fun AllUserListItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.username.first().uppercase(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {

                },
                modifier = Modifier.height(32.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.chat),
                    style = TextStyle(fontSize = 12.sp),
                    color = Color.White,
                    maxLines = 1
                )
            }
        }
    }
}









