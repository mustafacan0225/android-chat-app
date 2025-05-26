package com.mustafacan.core.domain.model.socket

sealed class SocketMessage {
    data class OnlineUsers(val users: List<OnlineUser>) : SocketMessage()
}