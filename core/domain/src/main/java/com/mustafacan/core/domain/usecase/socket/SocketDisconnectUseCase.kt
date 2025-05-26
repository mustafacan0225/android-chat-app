package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.domain.service.SocketService
import javax.inject.Inject

class SocketDisconnectUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): Result<Unit> {
        return try {
            socketService.disconnect()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}