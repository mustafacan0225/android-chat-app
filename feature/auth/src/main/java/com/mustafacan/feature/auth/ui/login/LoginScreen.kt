package com.mustafacan.feature.auth.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mustafacan.feature.auth.R
import com.mustafacan.core.ui.component.button.DefaultButtonColors
import com.mustafacan.core.ui.component.textfield.DefaultTextFieldColors
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LoginRoute(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {
                is LoginUiEffect.NavigateToRegister -> {
                    navController.navigate(NavDestinationItem.Register)
                }
                is LoginUiEffect.NavigateToHome -> {
                    // TODO: Navigate to Home screen
                }
                is LoginUiEffect.ShowSnackbar -> {
                    // TODO: Show snackbar
                }
                null -> {}
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onEvent = { viewModel.sendEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Email Field
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onEvent(LoginUiEvent.EmailChanged(it)) },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = DefaultTextFieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onEvent(LoginUiEvent.PasswordChanged(it)) },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (uiState.isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (uiState.isPasswordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)

                IconButton(onClick = { onEvent(LoginUiEvent.TogglePasswordVisibility) }) {
                    Icon(imageVector = image, contentDescription = description, tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { onEvent(LoginUiEvent.LoginClicked) }
            ),
            colors = DefaultTextFieldColors
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = { onEvent(LoginUiEvent.LoginClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.isLoginButtonEnabled,
            colors = DefaultButtonColors
        ) {
            if (uiState.isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(stringResource(R.string.login), color = Color.White)
                }
            } else {
                Text(stringResource(R.string.login), color = if (uiState.isLoginButtonEnabled) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { onEvent(LoginUiEvent.RegisterClicked) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.navigate_to_register_text), color = MaterialTheme.colorScheme.primary)
        }
    }
}




