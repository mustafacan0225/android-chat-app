package com.mustafacan.data.network.datasource

import com.mustafacan.core.model.auth.RegisterRequest
import com.mustafacan.core.model.room.DirectMessageRoomsRequestModel
import com.mustafacan.data.network.api.service.RoomService
import com.mustafacan.data.network.util.ApiCallHandler
import javax.inject.Inject

class RoomsRemoteDataSource @Inject constructor(private val roomService: RoomService) :
    ApiCallHandler() {

    suspend fun getDirectMessageRooms(requestModel: DirectMessageRoomsRequestModel) = apiCall { roomService.directMessageRooms(requestModel) }
    suspend fun getGroupMessageRooms() = apiCall { roomService.groupMessageRooms() }
}