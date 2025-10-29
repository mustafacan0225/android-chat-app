package com.mustafacan.core.model.socket

import com.mustafacan.core.model.chat.Message
import com.mustafacan.core.model.chat.TypingModel
import com.mustafacan.core.model.users.User
import com.mustafacan.core.model.users.UserStatus

sealed class SocketMessage {
    data class OnlineUsers(val users: List<User>) : SocketMessage()
    data class UserStatusUpdated(val userStatus: UserStatus): SocketMessage()
    data class ReceivedMessage(val message: Message): SocketMessage()
    data class Typing(val typingModel: TypingModel): SocketMessage()
    data class StopTyping(val typingModel: TypingModel): SocketMessage()

}