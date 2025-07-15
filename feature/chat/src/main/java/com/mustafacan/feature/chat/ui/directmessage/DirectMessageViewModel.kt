package com.mustafacan.feature.chat.ui.directmessage

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.usecase.socket.ObserveUserStatusUseCase
import com.mustafacan.core.domain.usecase.socket.SocketEmitEventUseCase
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DirectMessageViewModel @Inject constructor(@ApplicationContext private val context: Context,
                                                 private val savedStateHandle: SavedStateHandle,
                                                 private val observeUserStatusUseCase: ObserveUserStatusUseCase,
    private val socketEmitEventUseCase: SocketEmitEventUseCase): BaseViewModel<DirectMessageUiState, DirectMessageUiEvent, DirectMessageUiEffect>(initialState = DirectMessageUiState()) {

    init {
        val receiverUser: UserUiModel? = savedStateHandle["user"]
        val previousPage: String = savedStateHandle["previousPage"]!!
        viewModelScope.launch {

            setState { copy(previousPage = previousPage) }
            setScaffoldBarsVisibility(false)
            delay(1500)
            setState { copy(receiverUser = receiverUser, initialProgressVisibility = false) }
            subscribeUserStatus()
            observeUserStatus()
        }


    }

    override fun handleEvent(event: DirectMessageUiEvent) {

    }

    fun subscribeUserStatus() {
        viewModelScope.launch {
            socketEmitEventUseCase.invoke(SocketEvent.SUBSCRIBE_USER_STATUS, uiState.value.receiverUser!!.id)
        }
    }

    suspend fun unSubscribeUserStatus() {
        socketEmitEventUseCase.invoke(SocketEvent.UNSUBSCRIBE_USER_STATUS, uiState.value.receiverUser!!.id)
    }

    fun observeUserStatus() {
        viewModelScope.launch {
            observeUserStatusUseCase().collect { userStatus ->
                Log.d("incomingstatus:", userStatus.status)
                Log.d("incomingstatus:", userStatus.userId)
                Log.d("incomingstatus:", uiState.value.receiverUser?.id?: "")
                if (userStatus.userId.equals(uiState.value.receiverUser?.id)) {
                    setState { copy(receiverUserStatus = if (userStatus.status.equals("online")) context.getString(R.string.connection_state_online) else context.getString(R.string.connection_state_offline),
                        receiverUserStatusColor = if (userStatus.status.equals("online")) Color.Green else Color.Gray) }
                }
            }
        }
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
                if (uiState.value.previousPage.contains(NavDestinationItem.AllUsers::class.qualifiedName?: "NavDestinationItem.AllUsers")
                    || uiState.value.previousPage.contains(NavDestinationItem.OnlineUsers::class.qualifiedName?: "NavDestinationItem.OnlineUsers")) {
                    RootScaffoldController.emit(ScaffoldEvent.SetTopAppBarVisibility(true))
                    RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(false))
                } else {
                    RootScaffoldController.emit(ScaffoldEvent.SetTopAppBarVisibility(true))
                    RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(true))
                }
            }

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