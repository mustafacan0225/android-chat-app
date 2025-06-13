package com.mustafacan.android_chat_app.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.model.socket.SocketConnectionState
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.domain.usecase.socket.ObserveSocketConnectionUseCase
import com.mustafacan.core.domain.usecase.socket.SocketConnectUseCase
import com.mustafacan.core.domain.usecase.socket.SocketDisconnectUseCase
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.component.dialog.DialogModel
import com.mustafacan.core.ui.component.dialog.DialogType
import com.mustafacan.core.ui.component.scaffold.RootScaffoldController
import com.mustafacan.core.ui.component.scaffold.ScaffoldEvent

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val socketConnectUseCase: SocketConnectUseCase,
    private val socketDisconnectUseCase: SocketDisconnectUseCase,
    private val observeSocketConnectionUseCase: ObserveSocketConnectionUseCase,
    private val getLocalUserUseCase: GetLocalUserUseCase
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>(initialState = HomeUiState()) {

    init {
        observeSocketConnectionState()
        getUserName()
        observerScaffoldController()
    }

    override fun handleEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ConnectSocket -> {
                connect()
            }

            is HomeUiEvent.DisconnectSocket -> {
                disconnect()
            }

            is HomeUiEvent.DismissDialog -> {
                setState { copy(dialogModel = null) }
            }

            is HomeUiEvent.ShowDialog -> {
                setState { copy(dialogModel = event.dialogModel) }
            }

            is HomeUiEvent.SetTopAppBarContent -> {
                setState { copy(topBarContent = event.content) }
            }

        }
    }

    private fun observeSocketConnectionState() {
        viewModelScope.launch {
            observeSocketConnectionUseCase().collect { state ->
                Log.d("SocketConnection", "${state.name} on app module")
                if (uiState.value.socketConnectionState == SocketConnectionState.CONNECTING
                    && state != SocketConnectionState.CONNECTING) {

                    //optional(for loading anim)
                    delay(2000)
                }
                setState { copy(socketConnectionState = state) }

                if (state == SocketConnectionState.ERROR) {
                    sendEvent(
                        HomeUiEvent.ShowDialog(
                            dialogModel = DialogModel(
                                message = context.getString(R.string.error_socket_connection),
                                dialogType = DialogType.Confirm,
                                confirmText = context.getString(R.string.try_again),
                                onConfirm = {
                                    sendEvent(HomeUiEvent.DismissDialog)
                                    sendEvent(HomeUiEvent.ConnectSocket)
                                },
                                onCancel = {
                                    sendEvent(HomeUiEvent.DismissDialog)
                                },
                                isCancelable = false
                            )
                        )
                    )
                } else {
                    sendEvent(HomeUiEvent.DismissDialog)
                }

            }
        }
    }

    fun observerScaffoldController() {
        viewModelScope.launch {
            RootScaffoldController.events.collect { event ->
                when (event) {
                    is ScaffoldEvent.SetTopBar -> {
                        setState { copy(topBarContent = event.content) }
                    }

                    is ScaffoldEvent.SetBottomBarVisibility -> {
                        setState { copy(bottomBarVisibility = event.visible) }
                    }
                }

            }
        }
    }

    fun connect() {
        viewModelScope.launch {
            setState { copy(socketConnectionState = SocketConnectionState.CONNECTING) }
            socketConnectUseCase()
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            socketDisconnectUseCase()
        }
    }

    fun getUserName() {
        viewModelScope.launch {
            getLocalUserUseCase()?.let {
                setState { copy(username = it.username) }
            }
        }
    }
}