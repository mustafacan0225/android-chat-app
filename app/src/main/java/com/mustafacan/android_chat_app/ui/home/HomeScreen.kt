package com.mustafacan.android_chat_app.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mustafacan.core.ui.R
import com.mustafacan.android_chat_app.ui.bottommenu.BottomMenu
import com.mustafacan.android_chat_app.ui.navigation.HomeNavHost
import com.mustafacan.core.domain.model.socket.SocketConnectionState
import com.mustafacan.core.ui.animation.lottie.LottieAnimation
import com.mustafacan.core.ui.component.dialog.ShowDialog
import com.mustafacan.core.ui.theme.PrimaryDark

@Composable
fun HomeRoute(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START ->  {
                    Log.d("LifecycleObserver", "ON_START -> ConnectSocket event sent")
                    viewModel.sendEvent(HomeUiEvent.DismissDialog)
                    viewModel.sendEvent(HomeUiEvent.ConnectSocket)
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("LifecycleObserver", "ON_STOP -> DisconnectSocket event sent")
                    viewModel.sendEvent(HomeUiEvent.DisconnectSocket)
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
            viewModel.sendEvent(HomeUiEvent.DisconnectSocket)
        }
    }

    HomeScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) }, navController)
}

@Composable
fun HomeScreen(uiState: HomeUiState, onEvent: (HomeUiEvent) -> Unit, navController: NavHostController) {


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(bottomBar = {
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
                HomeNavHost(navController = navController)
            }

        }

        if (uiState.connectionState == SocketConnectionState.CONNECTING) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .zIndex(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(R.raw.lottie_splash, Modifier.width(50.dp).height(50.dp))
                Text(stringResource(R.string.socket_connecting), color = PrimaryDark)
            }
        }

        uiState.dialogModel?.let { dialog ->
            ShowDialog(
                message = dialog.message,
                dialogType = dialog.dialogType,
                onDismiss = {
                    onEvent(HomeUiEvent.DismissDialog)
                    dialog.onDismiss?.invoke()
                }, onConfirm = {
                    onEvent(HomeUiEvent.DismissDialog)
                    dialog.onConfirm?.invoke()
                }, onCancel = {
                    onEvent(HomeUiEvent.DismissDialog)
                    dialog.onCancel?.invoke()
                }, confirmText = dialog.confirmText?: stringResource(android.R.string.ok),
                isCancelable = false
            )
        }
    }

}