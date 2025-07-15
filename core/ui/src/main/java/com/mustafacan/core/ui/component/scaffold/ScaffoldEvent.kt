package com.mustafacan.core.ui.component.scaffold

import androidx.compose.runtime.Composable

sealed class ScaffoldEvent {
    data class SetTopAppBarVisibility(val visible: Boolean) : ScaffoldEvent()
    data class SetBottomBarVisibility(val visible: Boolean) : ScaffoldEvent()
}