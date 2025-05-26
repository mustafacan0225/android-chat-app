package com.mustafacan.core.domain.model.socket

enum class SocketEvent(val eventName: String) {
    JOIN_USER("joinUser"),
    UPDATE_USER_STATUS("updateUserStatus"),
    ONLINE_USERS("onlineUsers")
}