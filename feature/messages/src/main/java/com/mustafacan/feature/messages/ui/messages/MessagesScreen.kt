package com.mustafacan.feature.messages.ui.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle

@Composable
fun MessagesRoute(
    viewModel: MessagesViewModel,
    navController: NavHostController,
    parentNavController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->


        }
    }

    MessagesScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) })
}

@Composable
fun MessagesScreen(uiState: MessagesUiState,
                   onEvent: (MessagesUiEvent) -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("New Messages Page, Welcome")
    }
}