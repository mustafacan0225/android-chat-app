package com.mustafacan.feature.users.ui.onlineusers

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.model.socket.OnlineUser
import com.mustafacan.core.domain.model.socket.SocketConnectionState
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.domain.usecase.socket.GetOnlineUsersUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveSocketConnectionUseCase
import com.mustafacan.core.domain.usecase.socket.SocketConnectUseCase
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.component.scaffold.RootScaffoldController
import com.mustafacan.core.ui.component.scaffold.ScaffoldEvent
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class OnlineUsersViewModel @Inject constructor(@ApplicationContext private val context: Context,
                                               private val getOnlineUsersUseCase: GetOnlineUsersUseCase,
                                               private val getLocalUserUseCase: GetLocalUserUseCase,
                                               private val observeSocketConnectionUseCase: ObserveSocketConnectionUseCase,
                                               private val socketConnectUseCase: SocketConnectUseCase,) :
    BaseViewModel<OnlineUsersUiState, OnlineUsersUiEvent, OnlineUsersUiEffect>(initialState = OnlineUsersUiState()) {

    init {
        observeSocketConnectionState()
        getUserInfo()
        observeOnlineUsers()

        viewModelScope.launch {
            RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(false))
        }
    }

    override fun handleEvent(event: OnlineUsersUiEvent) {
        when (event) {
            OnlineUsersUiEvent.DismissDialog -> {
                setState { copy(dialogModel = null) }
            }

            is OnlineUsersUiEvent.ShowDialog -> {
                setState { copy(dialogModel = event.dialogModel) }
            }

            OnlineUsersUiEvent.ConnectSocket -> {
                setState { copy(socketConnectionState = SocketConnectionState.CONNECTING) }
                socketConnect()
            }

            is OnlineUsersUiEvent.Search -> {
                search(event.query)
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
                if (uiState.value.searchedText.isNotBlank()) {
                    search(query = uiState.value.searchedText)
                }
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

    fun search(query: String) {
        viewModelScope.launch {
            var result: List<OnlineUser> = listOf()
            if (query.isEmpty()) {
                result = uiState.value.onlineUsers
            } else {
                result = uiState.value.onlineUsers!!.filter {
                    it.name?.lowercase()?.contains(query.lowercase()) ?: false
                }
            }

            setState { copy(searchedOnlineUsers = result, searchedText = query) }
        }
    }

    override fun onCleared() {
        runBlocking {
            RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(true))
        }
        super.onCleared()
    }
}