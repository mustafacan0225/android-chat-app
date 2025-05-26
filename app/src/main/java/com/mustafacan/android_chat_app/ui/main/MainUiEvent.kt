package com.mustafacan.android_chat_app.ui.main

import com.mustafacan.core.ui.component.dialog.DialogModel

sealed class MainUiEvent {
    data class ShowDialog(val dialogModel: DialogModel) : MainUiEvent()
    object DismissDialog : MainUiEvent()
}
