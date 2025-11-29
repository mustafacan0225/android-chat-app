package com.mustafacan.data.network.api.service

import com.mustafacan.core.model.room.DirectMessageRoomsRequestModel
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import com.mustafacan.core.model.room.GroupMessageRoomsResponseModel
import com.mustafacan.data.network.api.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RoomService {

    @POST("direct-message-rooms")
    suspend fun directMessageRooms(
        @Body request: DirectMessageRoomsRequestModel
    ): Response<ApiResponse<List<DirectMessageRoomsResponseModel>>>

    @GET("rooms")
    suspend fun groupMessageRooms(): Response<ApiResponse<List<GroupMessageRoomsResponseModel>>>
}