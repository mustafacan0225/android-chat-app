package com.mustafacan.feature.rooms.ui.rooms

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.usecase.api.GetGroupMessageRoomsUseCase
import com.mustafacan.core.domain.usecase.datastore.GetHasNewGroupMessageUseCase
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.domain.usecase.datastore.SaveHasNewGroupMessageUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveGroupMessageRoomUpdatedUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveSocketConnectionUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveStopTypingUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveTypingUseCase
import com.mustafacan.core.domain.usecase.socket.SocketConnectUseCase
import com.mustafacan.core.domain.usecase.socket.SocketEmitEventUseCase
import com.mustafacan.core.model.chat.TypingChannelType
import com.mustafacan.core.model.chat.UserRef
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.model.socket.SocketEvent
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import com.mustafacan.feature.rooms.ui.rooms.mapper.toUiModel
import com.mustafacan.feature.rooms.ui.rooms.model.GroupMessageRoomUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getGroupMessageRoomsUseCase: GetGroupMessageRoomsUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase,
    private val observeSocketConnectionUseCase: ObserveSocketConnectionUseCase,
    private val observeGroupMessageRoomUpdatedUseCase: ObserveGroupMessageRoomUpdatedUseCase,
    private val socketConnectUseCase: SocketConnectUseCase,
    private val observeTypingUseCase: ObserveTypingUseCase,
    private val observeStopTypingUseCase: ObserveStopTypingUseCase,
    private val getHasNewGroupMessageUseCase: GetHasNewGroupMessageUseCase,
    private val saveHasNewGroupMessageUseCase: SaveHasNewGroupMessageUseCase,
    private val socketEmitEventUseCase: SocketEmitEventUseCase) : BaseViewModel<RoomsUiState, RoomsUiEvent, RoomsUiEffect>(initialState = RoomsUiState()) {
    init {
        viewModelScope.launch {
            val ownUserInfo = getOwnInfo()
            setState { copy(ownUser = ownUserInfo) }
            hasUnreadWhileTabClosed()
            getGroupMessageRooms()

        }

        observeSocketConnectionState()
        observeGroupMessageRoomUpdated()
        observeTyping()
        observeStopTyping()
    }

    override fun handleEvent(event: RoomsUiEvent) {
        when (event) {
            RoomsUiEvent.Retry -> {
                getGroupMessageRooms()
            }

            is RoomsUiEvent.NavigateToGroupMessage -> {
                sendEffect(RoomsUiEffect.NavigateToGroupMessage(event.user, event.room))
            }

            is RoomsUiEvent.SetHasNewMessage -> {
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

            RoomsUiEvent.ClearHasUnreadWhileTabClosed -> {
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
                Log.d("SocketConnection", "${state.name} on rooms module")

                if (uiState.value.socketConnectionState == SocketConnectionState.CONNECTING
                    && state != SocketConnectionState.CONNECTING) {
                    delay(2000)
                }

                if (state != SocketConnectionState.CONNECTED) {

                } else if (state == SocketConnectionState.CONNECTED) {
                    subscribeToRooms()
                }

                setState {
                    copy(socketConnectionState = state)
                }


            }
        }
    }

    private fun observeGroupMessageRoomUpdated() {
        viewModelScope.launch {
            observeGroupMessageRoomUpdatedUseCase().collect { room ->
                Log.d("GroupRoomUpdated", "${room._id} - ${room.lastMessage?.message} - ${room.lastMessage?.sender?.username}")
                val hasNewMessage = if (!(room.lastMessage?.sender?._id ?: "").equals(uiState.value.ownUser?.id)) true else false
                //val roomUiModel = room.toUiModel(hasNewMessage = hasNewMessage)
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
                }

                setState {
                    copy(messageRooms = currentRooms)
                }

                if (hasNewMessage)
                    sendEffect(RoomsUiEffect.ScrollToTop)

            }
        }
    }

    fun observeTyping() {
        viewModelScope.launch {
            observeTypingUseCase().collect { model ->
                val room = findRoomById(model.roomId?: "")
                room?.let {
                    if (model.channelType.equals(TypingChannelType.GROUP.type) && !uiState.value.typingRoomIds.contains(it.id)) {
                        Log.d("typing", "typing on rooms module")
                        setState { copy(typingRoomIds = typingRoomIds + it.id) }
                    }

                }
            }
        }
    }

    fun observeStopTyping() {
        viewModelScope.launch {
            observeStopTypingUseCase().collect { model ->
                val room = findRoomById(model.roomId?: "")
                room?.let {
                    if (model.channelType.equals(TypingChannelType.GROUP.type) && uiState.value.typingRoomIds.contains(it.id)) {
                        Log.d("typing", "stop on rooms module")
                        setState { copy(typingRoomIds = typingRoomIds - it.id) }
                    }

                }
            }
        }
    }

    fun findRoomById(
        id: String
    ): GroupMessageRoomUiModel? {
        return uiState.value.messageRooms.firstOrNull { room ->
            room.id == id
        }
    }

    fun getGroupMessageRooms() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            val result = getGroupMessageRoomsUseCase.invoke()
            result.onSuccess { response ->
                delay(2000)
                val uiList = response.map { it.toUiModel(false)}.toMutableList()
                Log.d("UnreadTabClosed", "1--- ${uiState.value.hasUnreadWhileTabClosed}")

                if (uiState.value.hasUnreadWhileTabClosed  && uiList.isNotEmpty())
                    uiList[0] = uiList[0].copy(hasNewMessage = true)
                setState { copy(loading = false, hasError = false, messageRooms = uiList) }
                subscribeToRooms()
            }.onFailure {
                delay(2000)
                setState { copy(loading = false, hasError = true) }
            }
        }
    }

    /*fun getOtherUserInfo(messageRoom: DirectMessageRoomsResponseModel): UserRef? {
        messageRoom.users.forEach {
            if (!it._id.equals(uiState.value.userId))
                return it
        }

        return null
    }*/

    fun hasUnreadWhileTabClosed() {
        viewModelScope.launch {
            val hasNewDirectMessage = getHasNewGroupMessageUseCase.invoke()
            Log.d("UnreadTabClosed", "2--- $hasNewDirectMessage")
            setState { copy(hasUnreadWhileTabClosed = hasNewDirectMessage) }
        }
    }

    fun clearHasUnreadWhileTabClosed() {
        viewModelScope.launch {
            saveHasNewGroupMessageUseCase.invoke(false)
        }
    }

    suspend fun subscribeToRooms() {
        uiState.value?.messageRooms?.forEach {
            socketEmitEventUseCase.invoke(
                SocketEvent.JOIN_ROOM,
                it.id
            )
        }

    }

}