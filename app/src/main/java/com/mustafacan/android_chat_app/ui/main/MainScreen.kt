package com.mustafacan.android_chat_app.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.mustafacan.android_chat_app.ui.navigation.AppNavHost
import com.mustafacan.core.common.app_event.AppEvent
import com.mustafacan.core.common.app_event.AppEventManager
import com.mustafacan.core.ui.navigation.NavDestinationItem

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
    AppNavHost(navController)
    ListenAppEvent(viewModel, navController)
}

@Composable
fun ListenAppEvent(viewModel: MainViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        AppEventManager.events.collect { event ->
            when (event) {
                AppEvent.TokenExpired -> {
                    navController.navigate(NavDestinationItem.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }

            }
        }
    }

}