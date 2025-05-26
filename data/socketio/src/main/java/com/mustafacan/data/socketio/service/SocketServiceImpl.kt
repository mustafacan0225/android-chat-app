package com.mustafacan.data.socketio.service

import com.mustafacan.core.domain.error.SocketError
import com.mustafacan.core.domain.model.socket.OnlineUser
import com.mustafacan.core.domain.model.socket.SocketConnectionState
import io.socket.client.Socket
import com.mustafacan.core.domain.model.socket.SocketEvent
import com.mustafacan.core.domain.model.socket.SocketMessage
import com.mustafacan.core.domain.service.SocketService
import com.mustafacan.data.socketio.factory.SocketFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SocketServiceImpl @Inject constructor(
    private val socketFactory: SocketFactory,
    private val moshi: Moshi
) : SocketService {

    private var socket: Socket? = null

    private val _incomingEvents = MutableSharedFlow<SocketMessage>()
    override val incomingEvents: SharedFlow<SocketMessage> = _incomingEvents.asSharedFlow()

    private val _connectionState = MutableStateFlow(SocketConnectionState.CONNECTING)
    override val connectionState: StateFlow<SocketConnectionState> = _connectionState.asStateFlow()

    override suspend fun connect() {
        try {
            socket?.off()
            socket?.disconnect()
            socket = socketFactory.create()
            observeConnection()
            observeEvents()
            _connectionState.value = SocketConnectionState.CONNECTING
            socket?.connect()
        } catch (e: Exception) {
            throw SocketError.ConnectionError
        }

    }

    override fun disconnect() {
        socket?.off()
        socket?.disconnect()
        socket = null
    }

    override fun emitEvent(event: SocketEvent, data: Any) {
        socket?.emit(event.eventName, data)
    }

    private fun observeConnection() {
        socket?.on(Socket.EVENT_CONNECT) {
            _connectionState.value = SocketConnectionState.CONNECTED
        }
        socket?.on(Socket.EVENT_DISCONNECT) {
            _connectionState.value = SocketConnectionState.DISCONNECTED
        }
        socket?.on(Socket.EVENT_CONNECT_ERROR) {
            _connectionState.value = SocketConnectionState.ERROR
        }
    }

    private fun observeEvents() {
        socket?.on(SocketEvent.ONLINE_USERS.eventName) { args ->
            args.firstOrNull()?.let { rawData ->
                try {
                    val result = moshi.adapter<List<OnlineUser>>(Types.newParameterizedType(List::class.java, OnlineUser::class.java))
                        .fromJson(rawData.toString()) ?: listOf()
                    result?.let {
                        _incomingEvents.tryEmit(SocketMessage.OnlineUsers(it))
                    }
                } catch (e: Exception) {

                }

            }
        }
        // diğer event'ler tek tek eklenir (emit edilenler dinlenmez burada)
    }
}