package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.model.socket.SocketMessage
import com.mustafacan.core.domain.service.SocketService
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ObserveDirectMessageRoomUpdatedUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): Flow<DirectMessageRoomsResponseModel> {
        return socketService.incomingEvents.mapNotNull { (it as? SocketMessage.DirectMessageRoomUpdated)?.room }
    }
}