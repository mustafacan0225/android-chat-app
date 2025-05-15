package com.mustafacan.android_chat_app.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mustafacan.android_chat_app.ui.navigation.AppNavHost
import com.mustafacan.core.common.app_event.AppEvent
import com.mustafacan.core.common.app_event.AppEventManager
import com.mustafacan.core.ui.component.dialog.BaseDialog
import com.mustafacan.core.ui.navigation.NavDestinationItem

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    AppNavHost(navController)
    ListenAppEvent(viewModel, navController)

}

@Composable
fun ListenAppEvent(viewModel: MainViewModel, navController: NavHostController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        AppEventManager.events.collect { event ->
            when (event) {
                AppEvent.TokenExpired -> {
                    navController.navigate(NavDestinationItem.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }

                is AppEvent.ShowPopup -> {
                    viewModel.sendEvent(MainUiEvent.ShowPopup(event))
                }
            }
        }
    }

    uiState.showPopupEvent?.let { popupEvent ->
        BaseDialog(
            message = popupEvent.message,
            popupType = popupEvent.popupType,
            onDismiss = {
                viewModel.sendEvent(MainUiEvent.DismissPopup)
                popupEvent.onDismiss?.invoke()
            }, onConfirm = {
                viewModel.sendEvent(MainUiEvent.DismissPopup)
                popupEvent.onConfirm?.invoke()
            }, onCancel = {
                viewModel.sendEvent(MainUiEvent.DismissPopup)
                popupEvent.onCancel?.invoke()
            }
        )
    }
}