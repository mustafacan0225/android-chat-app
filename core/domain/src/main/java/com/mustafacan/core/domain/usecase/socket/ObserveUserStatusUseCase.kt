package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.model.socket.SocketMessage
import com.mustafacan.core.domain.service.SocketService
import com.mustafacan.core.model.users.UserStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ObserveUserStatusUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): Flow<UserStatus> {
        return socketService.incomingEvents.mapNotNull { (it as? SocketMessage.UserStatusUpdated)?.userStatus }
    }
}