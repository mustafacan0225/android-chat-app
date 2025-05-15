package com.mustafacan.android_chat_app.ui.main

import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :
    BaseViewModel<MainUiState, MainUiEvent, MainUiEffect>(initialState = MainUiState()) {
    override fun handleEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.ShowPopup -> {
                setState { copy(showPopupEvent = event.showPopupEvent) }
            }

            is MainUiEvent.DismissPopup -> {
                setState { copy(showPopupEvent = null) }
            }
        }
    }

}