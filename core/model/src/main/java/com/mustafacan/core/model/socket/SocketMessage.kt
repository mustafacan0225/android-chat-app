package com.mustafacan.core.model.socket

import com.mustafacan.core.model.users.User

sealed class SocketMessage {
    data class OnlineUsers(val users: List<User>) : SocketMessage()
}