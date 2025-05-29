package com.mustafacan.android_chat_app.ui.home

import androidx.compose.runtime.Composable
import com.mustafacan.core.domain.model.socket.SocketConnectionState
import com.mustafacan.core.ui.component.dialog.DialogModel

data class HomeUiState(
    val connectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
    val dialogModel: DialogModel? = null,
    val bottomBarVisibility: Boolean = true,
    val username: String = "",
    val topBarContent: (@Composable () -> Unit)? = null
)