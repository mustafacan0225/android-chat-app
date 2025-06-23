package com.mustafacan.data.network.api.service

import com.mustafacan.core.model.users.PagedUserResponse
import com.mustafacan.core.model.users.UserSearchRequest
import com.mustafacan.data.network.api.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @GET("all-users")
    suspend fun getPaginatedAllUsers(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<ApiResponse<PagedUserResponse>>

    @POST("search-users")
    suspend fun getPaginatedSearchedUsers(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Body body: UserSearchRequest
    ): Response<ApiResponse<PagedUserResponse>>

}