package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.model.socket.SocketMessage
import com.mustafacan.core.model.users.User
import com.mustafacan.core.domain.service.SocketService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetOnlineUsersUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): Flow<List<User>> {
        return socketService.incomingEvents.mapNotNull { (it as? SocketMessage.OnlineUsers)?.users }
    }
}