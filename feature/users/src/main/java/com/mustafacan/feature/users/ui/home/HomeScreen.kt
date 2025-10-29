package com.mustafacan.feature.users.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.model.users.User
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.component.error.ErrorView
import com.mustafacan.core.ui.component.header.ListHeaderItem
import com.mustafacan.core.ui.component.loading.HorizontalCircleShimmer
import com.mustafacan.core.ui.component.loading.MoreItemsLoading
import com.mustafacan.core.ui.component.loading.VerticalRectangleShimmer
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle
import com.mustafacan.feature.users.ui.common.HorizontalUserItem
import com.mustafacan.feature.users.ui.common.UserItem

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
                    navController.navigate(NavDestinationItem.AllUsers)
                }

                HomeUiEffect.NavigateToOnlineUsersPage -> {
                    navController.navigate(NavDestinationItem.OnlineUsers)
                }

                is HomeUiEffect.NavigateToDirectMessage -> {
                    parentNavController.navigate(NavDestinationItem.DirectMessage(own = viewModel.getOwnInfo(), receiverUser = effect.user, NavDestinationItem.Users::class.qualifiedName?: "NavDestinationItem.Users"))
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
        showAllText = (uiState.onlineUsers.size > 0 && uiState.socketConnectionState == SocketConnectionState.CONNECTED))

    Spacer(modifier = Modifier.height(16.dp))

    if (uiState.socketConnectionState == SocketConnectionState.CONNECTING) {
        HorizontalCircleShimmer()
    } else if (uiState.socketConnectionState == SocketConnectionState.CONNECTED) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.onlineUsers) { user ->
                HorizontalUserItem(user,
                    buttonClicked = {
                        onEvent(HomeUiEvent.NavigateToDirectMessage(user = UserUiModel(id = user.id, username = user.username)))

                }, isSelf = user.id.equals(uiState.userId))
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
            onClick = { onEvent(HomeUiEvent.NavigateToAllUsersPage) },
            showAllText = (allUsers.itemCount > 0 && (uiState.allUsersAppendError == null && uiState.allUsersLoadingError == null)))

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoadingAllUsers) {
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
                        UserItem(user,
                            buttonClicked = {
                                onEvent(HomeUiEvent.NavigateToDirectMessage(user = UserUiModel(id = user.id, username = user.username)))

                            }, isSelf = user.id?.equals(uiState.userId) == true
                        )
                    }
                }
            }

        }

        MoreItemsLoading(uiState.isAppendingAllUsers)

        if (uiState.allUsersAppendError != null) {
            ErrorView(message = stringResource(R.string.default_error),
                onRetry = {
                    onEvent(HomeUiEvent.RetryAllUsers(allUsers))
                })
        }

    }

}

@Composable
fun UsersNavigationWatcher(
    navController: NavHostController,
    onEvent: (HomeUiEvent) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow
            .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { backStackEntry ->
                val screenName = backStackEntry.destination.javaClass.simpleName
                Log.d("NavWatcher", "Ekran: $screenName")

                when (screenName) {
                    "Users" -> {
                        onEvent(HomeUiEvent.SetBottomBarVisibility(true))
                        onEvent(HomeUiEvent.SetTopAppBarVisibility(true))
                    }

                    "OnlineUsers", "AllUsers" -> {
                        onEvent(HomeUiEvent.SetBottomBarVisibility(false))
                        onEvent(HomeUiEvent.SetTopAppBarVisibility(true))
                    }

                    else -> {
                        Log.d("NavWatcher", "Bilinmeyen ekran: $screenName")
                    }
                }
            }
    }
}











