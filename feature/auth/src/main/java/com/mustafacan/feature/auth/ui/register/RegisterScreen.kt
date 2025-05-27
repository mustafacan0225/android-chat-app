package com.mustafacan.feature.auth.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mustafacan.core.ui.component.button.DefaultButtonColors
import com.mustafacan.core.ui.component.dialog.ShowDialog
import com.mustafacan.core.ui.component.textfield.DefaultTextFieldColors
import com.mustafacan.core.ui.navigation.NavDestinationItem
import com.mustafacan.core.ui.util.rememberFlowWithLifecycle
import com.mustafacan.feature.auth.R

@Composable
fun RegisterRoute(viewModel: RegisterViewModel = hiltViewModel(), navController: NavController, parentNavController: NavController) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEffect = rememberFlowWithLifecycle(viewModel.uiEffect)

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is RegisterUiEffect.NavigateToLogin -> {
                    navController.navigate(NavDestinationItem.Login) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }

                is RegisterUiEffect.NavigateToHome -> {
                    parentNavController.navigate(NavDestinationItem.Home) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    RegisterScreen(uiState = uiState, onEvent = { viewModel.sendEvent(it) })
}

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = stringResource(R.string.register), style = MaterialTheme.typography.headlineLarge)


        // UserName Field
        OutlinedTextField(
            value = uiState.username,
            onValueChange = { onEvent(RegisterUiEvent.UserNameChanged(it)) },
            label = { Text(stringResource(R.string.username)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = DefaultTextFieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { onEvent(RegisterUiEvent.EmailChanged(it)) },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = DefaultTextFieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onEvent(RegisterUiEvent.PasswordChanged(it)) },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (uiState.isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (uiState.isPasswordVisible) stringResource(R.string.hide_password) else stringResource(
                    R.string.show_password)

                IconButton(onClick = { onEvent(RegisterUiEvent.TogglePasswordVisibility) }) {
                    Icon(imageVector = image, contentDescription = description, tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { onEvent(RegisterUiEvent.RegisterClicked) }
            ),
            colors = DefaultTextFieldColors
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Register Button
        Button(
            onClick = { onEvent(RegisterUiEvent.RegisterClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.isRegisterButtonEnabled,
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
                    Text(stringResource(R.string.register), color = Color.White)
                }
            } else {
                Text(stringResource(R.string.register), color = if (uiState.isRegisterButtonEnabled) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { onEvent(RegisterUiEvent.LoginClicked) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.navigate_to_login_text), color = MaterialTheme.colorScheme.primary)
        }

        uiState.dialogModel?.let { dialogModel ->
            ShowDialog(dialogModel)
        }
    }
}