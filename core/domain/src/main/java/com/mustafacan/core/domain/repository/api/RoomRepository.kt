package com.mustafacan.core.domain.repository.api

import com.mustafacan.core.model.room.DirectMessageRoomsRequestModel
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import com.mustafacan.core.model.room.GroupMessageRoomsResponseModel

interface RoomRepository {
     suspend fun getDirectMessageRooms(request: DirectMessageRoomsRequestModel): Result<List<DirectMessageRoomsResponseModel>>
     suspend fun getGroupMessageRooms(): Result<List<GroupMessageRoomsResponseModel>>
}