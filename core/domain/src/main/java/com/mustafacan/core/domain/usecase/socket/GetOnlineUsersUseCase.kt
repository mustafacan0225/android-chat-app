package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.domain.model.socket.OnlineUser
import com.mustafacan.core.domain.model.socket.SocketMessage
import com.mustafacan.core.domain.service.SocketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetOnlineUsersUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): Flow<List<OnlineUser>> {
        return socketService.incomingEvents.mapNotNull { (it as? SocketMessage.OnlineUsers)?.users }
    }
}