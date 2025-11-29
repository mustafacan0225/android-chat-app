package com.mustafacan.feature.messages.ui.messages

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.usecase.api.GetAllUsersPagingDataUseCase
import com.mustafacan.core.domain.usecase.api.GetDirectMessageRoomsUseCase
import com.mustafacan.core.domain.usecase.datastore.GetHasNewDirectMessageUseCase
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.domain.usecase.datastore.SaveHasNewDirectMessageUseCase
import com.mustafacan.core.domain.usecase.socket.GetOnlineUsersUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveDirectMessageRoomUpdatedUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveSocketConnectionUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveStopTypingUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveTypingUseCase
import com.mustafacan.core.domain.usecase.socket.SocketConnectUseCase
import com.mustafacan.core.model.chat.UserRef
import com.mustafacan.core.model.room.DirectMessageRoomsRequestModel
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import com.mustafacan.feature.messages.ui.messages.mapper.toUiModel
import com.mustafacan.feature.messages.ui.messages.model.DirectMessageRoomUiModel
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
    private val observeDirectMessageRoomUseCase: ObserveDirectMessageRoomUpdatedUseCase,
    private val socketConnectUseCase: SocketConnectUseCase,
    private val observeTypingUseCase: ObserveTypingUseCase,
    private val observeStopTypingUseCase: ObserveStopTypingUseCase,
    private val getHasNewDirectMessageUseCase: GetHasNewDirectMessageUseCase,
    private val saveHasNewDirectMessageUseCase: SaveHasNewDirectMessageUseCase

    ) : BaseViewModel<MessagesUiState, MessagesUiEvent, MessagesUiEffect>(initialState = MessagesUiState()) {

    init {
        viewModelScope.launch {
            val ownUserInfo = getOwnInfo()
            setState { copy(userId = ownUserInfo.id) }
            hasUnreadWhileTabClosed()
            getDirectMessageRooms()

        }

        observeSocketConnectionState()
        observeDirectMessageRoom()
        observeTyping()
        observeStopTyping()
    }

    override fun handleEvent(event: MessagesUiEvent) {
            when (event) {
                MessagesUiEvent.Retry -> {
                    getDirectMessageRooms()
                }

                is MessagesUiEvent.NavigateToDirectMessage -> {
                    sendEffect(MessagesUiEffect.NavigateToDirectMessage(event.user))
                }

                is MessagesUiEvent.SetHasNewMessage -> {
                    val updatedRoom = event.messageRoomUiModel.copy(hasNewMessage = event.hasNewMessage)

                    setState {
                        copy(
                            messageRooms = uiState.value.messageRooms.map { room ->
                                if (room.id == updatedRoom.id) {
                                    updatedRoom
                                } else {
                                    room
                                }
                            }
                        )
                    }
                }

                MessagesUiEvent.ClearHasUnreadWhileTabClosed -> {
                    clearHasUnreadWhileTabClosed()
                }
            }
    }

    fun socketConnect() {
        viewModelScope.launch {
            setState { copy(socketConnectionState = SocketConnectionState.CONNECTING) }
            socketConnectUseCase()
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

    private fun observeDirectMessageRoom() {
        viewModelScope.launch {
            observeDirectMessageRoomUseCase().collect { room ->
                Log.d("DmRoomUpdated", "${room._id} - ${room.lastMessage?.message} - ${room.lastMessage?.sender?.username}")
                val hasNewMessage = if (!(room.lastMessage?.sender?._id ?: "").equals(uiState.value.userId)) true else false
                val roomUiModel = room.toUiModel(hasNewMessage = hasNewMessage)
                val currentRooms = uiState.value.messageRooms.toMutableList()

                val existingIndex = currentRooms.indexOfFirst { it.id == room._id }

                if (existingIndex != -1) {
                    val existingRoom = currentRooms[existingIndex].copy(
                        lastMessage = room.lastMessage,
                        updatedAt = room.createdAt,
                        hasNewMessage = hasNewMessage
                    )
                    currentRooms.removeAt(existingIndex)
                    currentRooms.add(0, existingRoom)
                } else {
                    currentRooms.add(0, roomUiModel)
                }

                setState {
                    copy(messageRooms = currentRooms)
                }

            }
        }
    }

    fun observeTyping() {
        viewModelScope.launch {
            observeTypingUseCase().collect { model ->
                val room = findRoomByUserId(model.sender)
                room?.let {
                    if (!uiState.value.typingRoomIds.contains(it.id))
                        setState { copy(typingRoomIds = typingRoomIds + it.id) }
                }
            }
        }
    }

    fun observeStopTyping() {
        viewModelScope.launch {
            observeStopTypingUseCase().collect { model ->
                val room = findRoomByUserId(model.sender)
                room?.let {
                    if (uiState.value.typingRoomIds.contains(it.id))
                        setState { copy(typingRoomIds = typingRoomIds - it.id) }
                }
            }
        }
    }

    fun findRoomByUserId(
        userId: String
    ): DirectMessageRoomUiModel? {
        return uiState.value.messageRooms.firstOrNull { room ->
            room.users.any { it._id == userId }
        }
    }

    fun getDirectMessageRooms() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            val result = getDirectMessageRoomsUseCase.invoke(request = DirectMessageRoomsRequestModel(userId = uiState.value.userId))
            result.onSuccess { response ->
                delay(2000)
                val uiList = response.map { it.toUiModel(false)}.toMutableList()
                Log.d("UnreadTabClosed", "1--- ${uiState.value.hasUnreadWhileTabClosed}")

                if (uiState.value.hasUnreadWhileTabClosed  && uiList.isNotEmpty())
                        uiList[0] = uiList[0].copy(hasNewMessage = true)
                setState { copy(loading = false, hasError = false, messageRooms = uiList) }
            }.onFailure {
                delay(2000)
                setState { copy(loading = false, hasError = true) }
            }
        }
    }

    fun getOtherUserInfo(messageRoom: DirectMessageRoomsResponseModel): UserRef? {
        messageRoom.users.forEach {
            if (!it._id.equals(uiState.value.userId))
                return it
        }

        return null
    }

    fun hasUnreadWhileTabClosed() {
        viewModelScope.launch {
            val hasNewDirectMessage = getHasNewDirectMessageUseCase.invoke()
            Log.d("UnreadTabClosed", "2--- $hasNewDirectMessage")
            setState { copy(hasUnreadWhileTabClosed = hasNewDirectMessage) }
        }
    }

    fun clearHasUnreadWhileTabClosed() {
        viewModelScope.launch {
            saveHasNewDirectMessageUseCase.invoke(false)
        }
    }
}