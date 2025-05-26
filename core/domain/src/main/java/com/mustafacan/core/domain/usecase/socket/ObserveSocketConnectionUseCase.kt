package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.domain.model.socket.SocketConnectionState
import com.mustafacan.core.domain.service.SocketService
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveSocketConnectionUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): StateFlow<SocketConnectionState> = socketService.connectionState
}