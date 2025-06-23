package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.model.socket.SocketEvent
import com.mustafacan.core.domain.service.SocketService
import javax.inject.Inject

class SocketEmitEventUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(event: SocketEvent, data: Any): Result<Unit> {
        return try {
            socketService.emitEvent(event, data)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}