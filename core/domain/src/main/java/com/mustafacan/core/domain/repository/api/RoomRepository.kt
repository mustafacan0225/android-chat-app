package com.mustafacan.core.domain.repository.api

import com.mustafacan.core.model.room.DirectMessageRoomsRequestModel
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel

interface RoomRepository {
     suspend fun getDirectMessageRooms(request: DirectMessageRoomsRequestModel): Result<List<DirectMessageRoomsResponseModel>>
     //fun getGroupMessageRooms(): Result<DmRoomsResponseModel>
}