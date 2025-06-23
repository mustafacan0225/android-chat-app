package com.mustafacan.core.domain.service

import com.mustafacan.core.model.socket.SocketConnectionState
import com.mustafacan.core.model.socket.SocketEvent
import com.mustafacan.core.model.socket.SocketMessage
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface SocketService {
    val incomingEvents: SharedFlow<SocketMessage>
    val connectionState: StateFlow<SocketConnectionState>

    suspend fun connect()
    fun disconnect()
    fun emitEvent(event: SocketEvent, data: Any)
}