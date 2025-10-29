package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.model.socket.SocketMessage
import com.mustafacan.core.domain.service.SocketService
import com.mustafacan.core.model.chat.TypingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ObserveTypingUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): Flow<TypingModel> {
        return socketService.incomingEvents.mapNotNull { (it as? SocketMessage.Typing)?.typingModel }
    }
}