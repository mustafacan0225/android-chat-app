package com.mustafacan.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE, EVENT, EFFECT>(
    initialState: STATE
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    private val _uiEffect = Channel<EFFECT>(Channel.BUFFERED)
    val uiEffect: Flow<EFFECT> = _uiEffect.receiveAsFlow()

    fun sendEvent(event: EVENT) {
        handleEvent(event)
    }

    protected fun setState(reducer: STATE.() -> STATE) {
        _uiState.update { it.reducer() }
    }

    protected fun sendEffect(effect: EFFECT) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    protected abstract fun handleEvent(event: EVENT)
}
