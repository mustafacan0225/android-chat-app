package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.domain.service.SocketService
import javax.inject.Inject

class SocketConnectUseCase @Inject constructor(
    private val socketService: SocketService
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            socketService.connect()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
