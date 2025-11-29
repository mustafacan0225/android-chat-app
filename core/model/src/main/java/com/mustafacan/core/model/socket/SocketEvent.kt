package com.mustafacan.core.model.socket

enum class SocketEvent(val eventName: String) {
    JOIN_USER("joinUser"),
    //UPDATE_USER_STATUS("updateUserStatus"),
    ONLINE_USERS("online_users"),

    USER_STATUS_UPDATE("user_status_update"),
    SUBSCRIBE_USER_STATUS("subscribe_user_status"),
    UNSUBSCRIBE_USER_STATUS("unsubscribe_user_status"),
    SEND_MESSAGE("send_message"),
    RECEIVE_MESSAGE("receive_message"),

    TYPING("typing"),
    STOP_TYPING("stop_typing"),

    DIRECT_MESSAGE_ROOM_UPDATED("direct_message_room_updated")
}