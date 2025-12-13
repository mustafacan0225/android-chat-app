package com.mustafacan.data.network.api.service

import com.mustafacan.core.model.chat.PagedMessageResponse
import com.mustafacan.data.network.api.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MessageService {

    @GET("messages/direct/paginated")
    suspend fun getPaginatedDirectMessages(
        @Query("senderId") senderId: String,
        @Query("receiverId") receiverId: String,
        @Query("beforeId") beforeId: String? = null,
    ): Response<ApiResponse<PagedMessageResponse>>

    @GET("messages/group/paginated")
    suspend fun getPaginatedGroupMessages(
        @Query("roomId") roomId: String,
        @Query("beforeId") beforeId: String? = null,
    ): Response<ApiResponse<PagedMessageResponse>>

}