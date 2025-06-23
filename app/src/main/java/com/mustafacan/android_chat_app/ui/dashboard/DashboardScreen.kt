package com.mustafacan.android_chat_app.ui.dashboard

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mustafacan.core.ui.R
import com.mustafacan.android_chat_app.ui.bottommenu.BottomMenu
import com.mustafacan.android_chat_app.ui.navigation.DashboardNavHost
import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.ui.component.dialog.ShowDialog
import com.mustafacan.core.ui.component.overlay.FullScreenLoadingOverlay
import com.mustafacan.core.ui.theme.PrimaryLight

@Composable
fun DashboardRoute(viewModel: DashboardViewModel, navController: NavHostController) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    Log.d("LifecycleObserver", "ON_START -> ConnectSocket event sent")
                    viewModel.sendEvent(DashboardUiEvent.DismissDialog)
                    viewModel.sendEvent(DashboardUiEvent.ConnectSocket)
                }

                Lifecycle.Event.ON_RESUME -> {
                    Log.d("LifecycleObserver", "ON_RESUME ->")
                    if (uiState.topBarContent == null) {
                        viewModel.sendEvent(DashboardUiEvent.SetTopAppBarContent(content = { DashboardScreenTopAppBar(uiState) }))
                    }

                }

                Lifecycle.Event.ON_STOP -> {
                    Log.d("LifecycleObserver", "ON_STOP -> DisconnectSocket event sent")
                    viewModel.sendEvent(DashboardUiEvent.DisconnectSocket)
                }

                else -> {
                    Log.d("LifecycleObserver", "Unhandled lifecycle event: $event")
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            Log.d("LifecycleObserver", "Observer removed -> DisconnectSocket event sent")
            viewModel.sendEvent(DashboardUiEvent.DisconnectSocket)
        }
    }

    DashboardScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) }, navController)
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onEvent: (DashboardUiEvent) -> Unit,
    navController: NavHostController
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(topBar = {
            uiState.topBarContent?.invoke()
        }, bottomBar = {
            AnimatedVisibility(
                visible = uiState.bottomBarVisibility,
                enter = slideInVertically(animationSpec = tween(1000), initialOffsetY = { it }),
                exit = slideOutVertically(animationSpec = tween(1000), targetOffsetY = { it }),
                content = {
                    BottomMenu(
                        navController = navController
                    )
                }
            )
        }) {
            Box(modifier = Modifier.padding(it)) {
                DashboardNavHost(navController = navController)
            }

        }

        if (uiState.socketConnectionState == SocketConnectionState.CONNECTING) {
            FullScreenLoadingOverlay(
                message = stringResource(R.string.socket_connecting),
                animationOrIconModifier = Modifier.size(75.dp)
            )
        }

        uiState.dialogModel?.let { dialogModel ->
            ShowDialog(dialogModel)
        }
    }

}

@Composable
fun DashboardScreenTopAppBar(uiState: DashboardUiState) {
    Column(Modifier.fillMaxWidth().background(PrimaryLight).padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row (Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = uiState.username, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = Color.White))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.welcome), style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.7f)))
        }

        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {

            val (statusText, statusColor) = when (uiState.socketConnectionState) {
                SocketConnectionState.CONNECTING -> stringResource(R.string.connection_state_connecting) to Color.Yellow
                SocketConnectionState.CONNECTED -> stringResource(R.string.connection_state_online) to Color.Green
                SocketConnectionState.DISCONNECTED -> stringResource(R.string.connection_state_disconnected) to Color.Red
                SocketConnectionState.ERROR -> stringResource(R.string.connection_state_error) to Color.Red
            }

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(statusColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.7f)
                )
            )
        }
    }


}