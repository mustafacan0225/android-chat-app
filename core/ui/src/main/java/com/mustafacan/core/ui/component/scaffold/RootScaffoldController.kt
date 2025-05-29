package com.mustafacan.core.ui.component.scaffold

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object RootScaffoldController {
    private val _events = MutableSharedFlow<ScaffoldEvent>()
    val events = _events.asSharedFlow()

    suspend fun emit(event: ScaffoldEvent) {
        _events.emit(event)
    }
}