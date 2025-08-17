package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.model.socket.SocketMessage
import com.mustafacan.core.domain.service.SocketService
import com.mustafacan.core.model.chat.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ObserveReceivedMessageUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): Flow<Message> {
        return socketService.incomingEvents.mapNotNull { (it as? SocketMessage.ReceivedMessage)?.message }
    }
}