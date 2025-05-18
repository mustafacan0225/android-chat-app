package com.mustafacan.android_chat_app.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle

@Composable
fun SplashRoute(viewModel: SplashViewModel = hiltViewModel(), parentNavController: NavController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is SplashUiEffect.NavigateToLogin -> {
                    parentNavController.navigate(NavDestinationItem.Login) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }

                is SplashUiEffect.NavigateToHome -> {
                    parentNavController.navigate(NavDestinationItem.Home) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    SplashScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) })
}

@Composable
fun SplashScreen(uiState: SplashUiState, onEvent: (SplashUiEvent) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chat App Demo",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator()
        }
    }
}
