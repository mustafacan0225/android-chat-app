package com.mustafacan.android_chat_app.ui.home

import androidx.compose.runtime.Composable
import com.mustafacan.core.ui.component.dialog.DialogModel

sealed class HomeUiEvent {
    object ConnectSocket: HomeUiEvent()
    object DisconnectSocket: HomeUiEvent()
    data class ShowDialog(val dialogModel: DialogModel) : HomeUiEvent()
    object DismissDialog : HomeUiEvent()
    data class SetTopAppBarContent(val content:(@Composable () -> Unit)?) : HomeUiEvent()
}