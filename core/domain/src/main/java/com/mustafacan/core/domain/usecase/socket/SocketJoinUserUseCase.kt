package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.domain.service.SocketService
import javax.inject.Inject

class SocketJoinUserUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(userId: String): Result<Unit> {
        return try {
           // socketService.joinUser(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}