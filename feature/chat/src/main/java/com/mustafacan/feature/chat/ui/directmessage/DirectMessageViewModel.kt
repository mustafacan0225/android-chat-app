package com.mustafacan.feature.chat.ui.directmessage

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.mustafacan.core.domain.usecase.api.GetDirectMessagePagingDataUseCase
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveReceivedMessageUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveUserStatusUseCase
import com.mustafacan.core.domain.usecase.socket.SocketEmitEventUseCase
import com.mustafacan.core.model.chat.Message
import com.mustafacan.core.model.chat.MessageRequestModel
import com.mustafacan.core.model.chat.MessageType
import com.mustafacan.core.model.socket.SocketEvent
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.component.scaffold.RootScaffoldController
import com.mustafacan.core.ui.component.scaffold.ScaffoldEvent
import com.mustafacan.core.ui.model.UserUiModel
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class DirectMessageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle,
    private val observeUserStatusUseCase: ObserveUserStatusUseCase,
    private val observeReceivedMessageUseCase: ObserveReceivedMessageUseCase,
    private val getDirectMessagePagingDataUseCase: GetDirectMessagePagingDataUseCase,
    private val socketEmitEventUseCase: SocketEmitEventUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase

) : BaseViewModel<DirectMessageUiState, DirectMessageUiEvent, DirectMessageUiEffect>(initialState = DirectMessageUiState()) {

    /*val messagesPagingDataFlow: Flow<PagingData<Message>> =
        getDirectMessagePagingDataUseCase().cachedIn(viewModelScope)*/
    lateinit var messagesPagingDataFlow: Flow<PagingData<Message>>

    init {
        val receiverUser: UserUiModel? = savedStateHandle["user"]
        val previousPage: String = savedStateHandle["previousPage"]!!
        viewModelScope.launch {
            setState { copy(userId = "681b6a5ba251c55db40ce432") }
            messagesPagingDataFlow = getDirectMessagePagingDataUseCase(uiState.value.userId, receiverUser!!.id).cachedIn(viewModelScope)
            setState { copy(previousPage = previousPage) }
            setScaffoldBarsVisibility(false)
            delay(1500)
            setState { copy(receiverUser = receiverUser, initialProgressVisibility = false) }
            subscribeUserStatus()
            observeUserStatus()
            observeReceivedMessage()
        }


    }

    override fun handleEvent(event: DirectMessageUiEvent) {
        when (event) {
            is DirectMessageUiEvent.MessagesLoadStateChanged -> {
                messagesLoadStateChanged(event.messages)
            }

            is DirectMessageUiEvent.SendMessage -> {

                sendMessage(MessageRequestModel(type = MessageType.DIRECT_MESSAGE.value,
                    uiState.value.userId,
                    uiState.value.receiverUser!!.id,
                    uiState.value.messageValue,
                    ))
            }

            is DirectMessageUiEvent.MessageValueChanged -> {
                setState { copy(messageValue = event.message) }
            }
        }
    }

    fun subscribeUserStatus() {
        viewModelScope.launch {
            socketEmitEventUseCase.invoke(
                SocketEvent.SUBSCRIBE_USER_STATUS,
                uiState.value.receiverUser!!.id
            )
        }
    }

    suspend fun unSubscribeUserStatus() {
        socketEmitEventUseCase.invoke(
            SocketEvent.UNSUBSCRIBE_USER_STATUS,
            uiState.value.receiverUser!!.id
        )
    }

    fun sendMessage(messageRequestModel: MessageRequestModel) {
        viewModelScope.launch {
            val jsonMessage = JSONObject().apply {
                put("type", messageRequestModel.type)
                put("sender", messageRequestModel.sender)
                put("receiver", messageRequestModel.receiver)
                put("message", messageRequestModel.message)
                put("roomId", messageRequestModel.roomId) // null ise otomatik olarak JSONObject.NULL olur
            }

            socketEmitEventUseCase.invoke(
                SocketEvent.SEND_MESSAGE,
                jsonMessage
            )
        }
    }

    fun observeUserStatus() {
        viewModelScope.launch {
            observeUserStatusUseCase().collect { userStatus ->
                Log.d("incomingstatus:", userStatus.status)
                Log.d("incomingstatus:", userStatus.userId)
                Log.d("incomingstatus:", uiState.value.receiverUser?.id ?: "")
                if (userStatus.userId.equals(uiState.value.receiverUser?.id)) {
                    setState {
                        copy(
                            receiverUserStatus = if (userStatus.status.equals("online")) context.getString(
                                R.string.connection_state_online
                            ) else context.getString(R.string.connection_state_offline),
                            receiverUserStatusColor = if (userStatus.status.equals("online")) Color.Green else Color.Gray
                        )
                    }
                }
            }
        }
    }

    fun observeReceivedMessage() {
        viewModelScope.launch {
            observeReceivedMessageUseCase().collect { messageItem ->
                Log.d("SocketService*", "${messageItem.message} - ${messageItem.sender.username} in viewmodel")

                setState {
                    copy(socketMessages = socketMessages + messageItem, isMessageListEmpty = false)
                }

//                if (messageItem.sender._id.equals(uiState.value.userId))
//                    sendEffect(DirectMessageUiEffect.ScrollToBottom)
            }
        }
    }

    fun messagesLoadStateChanged(messages: LazyPagingItems<Message>) {
        val refresh = messages.loadState.refresh
        val append = messages.loadState.append
        val prepend = messages.loadState.prepend

        Log.d("LoadState***", "refresh: $refresh")
        Log.d("LoadState***", "append: $append")
        Log.d("LoadState***", "prepend: $prepend")
        Log.d("LoadState***", "itemCount: ${messages.itemCount}")

        setState {
            copy(
                isLoadingMessages = refresh is LoadState.Loading && messages.itemCount == 0,
                isPrependingMessages = prepend is LoadState.Loading,
                messagesLoadingError = (refresh as? LoadState.Error)?.error?.localizedMessage,
                messagesPrependError = (prepend as? LoadState.Error)?.error?.localizedMessage,
                isMessageListEmpty = refresh is LoadState.NotLoading &&
                        messages.itemCount == 0 &&
                        socketMessages.isEmpty()
            )
        }

        //if (refresh is LoadState.NotLoading && messages.itemCount > 0) {
        //    sendEffect(DirectMessageUiEffect.ScrollToBottom)
        //}
    }

    fun setScaffoldBarsVisibility(visible: Boolean? = null) {
        if (visible != null) {
            viewModelScope.launch {
                RootScaffoldController.emit(ScaffoldEvent.SetTopAppBarVisibility(visible))
                RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(visible))
            }
        } else {
            // Wrapped in runBlocking since this is called within onCleared, which is a non-suspending function
            runBlocking {
                if (uiState.value.previousPage.contains(
                        NavDestinationItem.AllUsers::class.qualifiedName
                            ?: "NavDestinationItem.AllUsers"
                    )
                    || uiState.value.previousPage.contains(
                        NavDestinationItem.OnlineUsers::class.qualifiedName
                            ?: "NavDestinationItem.OnlineUsers"
                    )
                ) {
                    RootScaffoldController.emit(ScaffoldEvent.SetTopAppBarVisibility(true))
                    RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(false))
                } else {
                    RootScaffoldController.emit(ScaffoldEvent.SetTopAppBarVisibility(true))
                    RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(true))
                }
            }

        }
    }

    fun updateScrollPosition(index: Int, offset: Int) {
        Log.d("messagedata:","first visible index: $index - offset: $offset")
        setState {
            copy(
                previousFirstVisibleItem = index,
                previousFirstVisibleItemOffset = offset
            )
        }
    }

    override fun onCleared() {
        runBlocking {
            unSubscribeUserStatus()
            setScaffoldBarsVisibility()
        }
        super.onCleared()
    }
}