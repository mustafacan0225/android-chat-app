package com.mustafacan.android_chat_app.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.model.socket.SocketConnectionState
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val socketConnectUseCase: SocketConnectUseCase,
    private val socketDisconnectUseCase: SocketDisconnectUseCase,
    private val observeSocketConnectionUseCase: ObserveSocketConnectionUseCase
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiEffect>(initialState = HomeUiState()) {

    init {
        observeConnectionState()
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
        }
    }

    private fun observeConnectionState() {
        viewModelScope.launch {
            observeSocketConnectionUseCase().collect { state ->
                setState { copy(connectionState = state) }
                Log.d("SocketConnection", state.name)
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

    fun connect() {
        viewModelScope.launch {
            setState { copy(connectionState = SocketConnectionState.CONNECTING) }
            delay(3000)
            socketConnectUseCase()
                .onSuccess {
                    setState { copy(connectionState = SocketConnectionState.CONNECTED) }
                }

        }
    }

    fun disconnect() {
        viewModelScope.launch {
            socketDisconnectUseCase()
        }
    }
}