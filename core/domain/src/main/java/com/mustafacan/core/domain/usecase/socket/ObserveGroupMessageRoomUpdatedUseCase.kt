package com.mustafacan.core.domain.usecase.socket

import com.mustafacan.core.model.socket.SocketMessage
import com.mustafacan.core.domain.service.SocketService
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import com.mustafacan.core.model.room.GroupMessageRoomsResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class ObserveGroupMessageRoomUpdatedUseCase @Inject constructor(
    private val socketService: SocketService
) {
    operator fun invoke(): Flow<GroupMessageRoomsResponseModel> {
        return socketService.incomingEvents.mapNotNull { (it as? SocketMessage.GroupMessageRoomUpdated)?.room }
    }
}