package com.mustafacan.core.model.socket

import com.mustafacan.core.model.users.User
import com.mustafacan.core.model.users.UserStatus

sealed class SocketMessage {
    data class OnlineUsers(val users: List<User>) : SocketMessage()
    data class UserStatusUpdated(val userStatus: UserStatus): SocketMessage()
}