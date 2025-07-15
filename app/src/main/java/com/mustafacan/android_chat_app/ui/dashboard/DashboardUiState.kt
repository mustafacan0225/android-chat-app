package com.mustafacan.android_chat_app.ui.dashboard

import androidx.compose.runtime.Composable
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.ui.component.dialog.DialogModel

data class DashboardUiState(
    val socketConnectionState: SocketConnectionState = SocketConnectionState.CONNECTING,
    val dialogModel: DialogModel? = null,
    val bottomBarVisibility: Boolean = true,
    val topAppBarVisibility: Boolean = true,
    val username: String = "",
)