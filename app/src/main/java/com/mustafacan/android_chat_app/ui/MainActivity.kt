package com.mustafacan.android_chat_app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mustafacan.android_chat_app.ui.navigation.AppNavHost
import com.mustafacan.core.common.app_event.AppEvent
import com.mustafacan.core.common.app_event.AppEventManager
import com.mustafacan.core.common.model.PopupType
import com.mustafacan.core.ui.component.dialog.BaseDialog
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavHost(navController)
                    ListenAppEvent(navController)
                }

            }
        }
    }

    @Composable
    fun ListenAppEvent(navController: NavHostController) {
        val dialogState = remember { mutableStateOf<AppEvent.ShowPopup?>(null) }
        LaunchedEffect(Unit) {
            AppEventManager.events.collect { event ->
                when (event) {
                    AppEvent.TokenExpired -> {
                        navController.navigate(NavDestinationItem.Login) {
                            popUpTo(0) { inclusive = true }
                        }
                    }

                    is AppEvent.ShowPopup -> {
                        dialogState.value = event
                    }
                }
            }
        }

        dialogState.value?.let { popupEvent ->
            BaseDialog(
                message = popupEvent.message,
                popupType = popupEvent.popupType,
                onDismiss = {
                    dialogState.value = null
                    popupEvent.onDismiss?.invoke()
                }, onConfirm = {
                    dialogState.value = null
                    popupEvent.onConfirm?.invoke()
                }, onCancel = {
                    dialogState.value = null
                    popupEvent.onCancel?.invoke()
                }
            )
        }


    }
}

