package com.mustafacan.feature.users.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.model.users.User
import com.mustafacan.core.domain.usecase.api.GetAllUsersPagingDataUseCase
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.domain.usecase.socket.GetOnlineUsersUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveSocketConnectionUseCase
import com.mustafacan.core.domain.usecase.socket.SocketConnectUseCase
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getOnlineUsersUseCase: GetOnlineUsersUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val getAllUsersPagingDataUseCase: GetAllUsersPagingDataUseCase,
    private val observeSocketConnectionUseCase: ObserveSocketConnectionUseCase,
    private val socketConnectUseCase: SocketConnectUseCase,
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>(initialState = HomeUiState()) {

    val allUsersPagingDataFlow: Flow<PagingData<User>> =
        getAllUsersPagingDataUseCase().cachedIn(viewModelScope)

    init {
        getUserInfo()
        observeSocketConnectionState()
        observeOnlineUsers()
    }

    override fun handleEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.DismissDialog -> {
                setState { copy(dialogModel = null) }
            }

            is HomeUiEvent.ShowDialog -> {
                setState { copy(dialogModel = event.dialogModel) }
            }

            is HomeUiEvent.AllUsersLoadStateChanged -> {
                allUsersLoadStateChanged(event.users)
            }

            HomeUiEvent.ConnectSocket -> {
                setState { copy(socketConnectionState = SocketConnectionState.CONNECTING) }
                socketConnect()
            }

            is HomeUiEvent.RetryAllUsers -> {
                event.users.retry()
            }

            HomeUiEvent.NavigateToOnlineUsersPage -> {
                sendEffect(HomeUiEffect.NavigateToOnlineUsersPage)
            }

            HomeUiEvent.NavigateToAllUsersPage -> {
                sendEffect(HomeUiEffect.NavigateToAllUsersPage)

            }
        }
    }

    fun socketConnect() {
        viewModelScope.launch {
            setState { copy(socketConnectionState = SocketConnectionState.CONNECTING) }
            socketConnectUseCase()
        }
    }

    fun observeOnlineUsers() {
        viewModelScope.launch {
            getOnlineUsersUseCase().collect { onlineUsers ->
                setState { copy(onlineUsers = onlineUsers, titleOnlineUsers = "${context.getString(R.string.online_users)} (${onlineUsers.size})") }
            }
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            getLocalUserUseCase()?.let {
                setState { copy(userId = it.id) }
            }
        }
    }

    private fun observeSocketConnectionState() {
        viewModelScope.launch {
            observeSocketConnectionUseCase().collect { state ->
                Log.d("SocketConnection", "${state.name} on users module")

                if (uiState.value.socketConnectionState == SocketConnectionState.CONNECTING
                    && state != SocketConnectionState.CONNECTING) {

                    //optional(for loading anim)
                    delay(2000)
                }

                if (state != SocketConnectionState.CONNECTED) {
                    setState { copy(titleOnlineUsers = context.getString(R.string.online_users)) }
                }

                setState {
                    copy(socketConnectionState = state)
                }


            }
        }
    }


    fun allUsersLoadStateChanged(users: LazyPagingItems<User>) {
        val refresh = users.loadState.refresh
        val append = users.loadState.append

        setState {
            copy(
                isLoadingAllUsers = refresh is LoadState.Loading && users.itemCount == 0,
                isAppendingAllUsers = append is LoadState.Loading,
                allUsersLoadingError = (refresh as? LoadState.Error)?.error?.localizedMessage,
                allUsersAppendError = (append as? LoadState.Error)?.error?.localizedMessage,
                isAllUsersListEmpty = refresh is LoadState.NotLoading && users.itemCount == 0
            )
        }
    }

}