package com.mustafacan.core.ui.component.scaffold

import androidx.compose.runtime.Composable

sealed class ScaffoldEvent {
    data class SetTopBar(val content: (@Composable () -> Unit)? = null) : ScaffoldEvent()
    data class SetBottomBarVisibility(val visible: Boolean) : ScaffoldEvent()
}