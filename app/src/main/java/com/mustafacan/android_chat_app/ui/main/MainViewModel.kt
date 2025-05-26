package com.mustafacan.android_chat_app.ui.main

import android.util.Log
import com.mustafacan.core.domain.usecase.socket.SocketDisconnectUseCase
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val disconnectUseCase: SocketDisconnectUseCase) :
    BaseViewModel<MainUiState, MainUiEvent, MainUiEffect>(initialState = MainUiState()) {
    override fun handleEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.ShowDialog -> {
                setState { copy(dialogModel = event.dialogModel) }
            }

            is MainUiEvent.DismissDialog -> {
                setState { copy(dialogModel = null) }
            }
        }
    }

    override fun onCleared() {
        Log.d("onDestroy", "onCleared")
        /*runBlocking {
            withContext(Dispatchers.IO) {
                disconnectUseCase()
            }
        }*/
        super.onCleared()
    }
}