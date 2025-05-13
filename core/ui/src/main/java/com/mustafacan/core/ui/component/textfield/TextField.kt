package com.mustafacan.core.ui.component.textfield

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable

val DefaultTextFieldColors: TextFieldColors
    @Composable get() = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedTextColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
        disabledTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
        disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        disabledLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
        focusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
    )