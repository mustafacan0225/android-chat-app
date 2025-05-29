package com.mustafacan.core.ui.component.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppBarAction(
    val imageVector: ImageVector,
    open val onClick: () -> Unit
) {

    data class SettingsAction(override val onClick: () -> Unit) : AppBarAction(
        imageVector = Icons.Filled.Settings,
        onClick = onClick
    )
}