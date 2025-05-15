package com.mustafacan.android_chat_app.ui.main

import com.mustafacan.core.common.app_event.AppEvent

sealed class MainUiEvent {
    data class ShowPopup(val showPopupEvent: AppEvent.ShowPopup) : MainUiEvent()
    object DismissPopup : MainUiEvent()
}
