package com.mustafacan.feature.messages.ui.messages

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.usecase.api.GetAllUsersPagingDataUseCase
import com.mustafacan.core.domain.usecase.api.GetDirectMessageRoomsUseCase
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.domain.usecase.socket.GetOnlineUsersUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveSocketConnectionUseCase
import com.mustafacan.core.domain.usecase.socket.SocketConnectUseCase
import com.mustafacan.core.model.room.DirectMessageRoomsRequestModel
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getDirectMessageRoomsUseCase: GetDirectMessageRoomsUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val observeSocketConnectionUseCase: ObserveSocketConnectionUseCase,
    private val socketConnectUseCase: SocketConnectUseCase,
) : BaseViewModel<MessagesUiState, MessagesUiEvent, MessagesUiEffect>(initialState = MessagesUiState()) {

    init {
        viewModelScope.launch {
            val ownUserInfo = getOwnInfo()
            setState { copy(userId = ownUserInfo.id) }
            getDirectMessageRooms()
        }

        observeSocketConnectionState()
    }

    override fun handleEvent(event: MessagesUiEvent) {

    }

    fun socketConnect() {
        viewModelScope.launch {
            setState { copy(socketConnectionState = SocketConnectionState.CONNECTING) }
            socketConnectUseCase()
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            getLocalUserUseCase()?.let {
                setState { copy(userId = it.id) }
            }
        }
    }

    suspend fun getOwnInfo() : UserUiModel {
        return withContext(Dispatchers.IO) {
            val authUser = getLocalUserUseCase.invoke()
            UserUiModel(authUser?.id?: "", authUser?.username?: "")
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

                }

                setState {
                    copy(socketConnectionState = state)
                }


            }
        }
    }


    suspend fun getDirectMessageRooms() {
        viewModelScope.launch {
            val result = getDirectMessageRoomsUseCase.invoke(request = DirectMessageRoomsRequestModel(userId = uiState.value.userId))
            result.onSuccess {
                Log.d("rooms***", "Direct Message Room Count: ${it.size}")
                Log.d("rooms***", "First room info: ${it.get(0).lastMessage?.sender?.username}: ${it.get(0).lastMessage?.message}")
            }.onFailure {
                Log.d("rooms***", it.message?: "hata")
            }
        }
    }
}