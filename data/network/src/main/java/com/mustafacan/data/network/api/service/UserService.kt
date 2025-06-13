package com.mustafacan.data.network.api.service

import com.mustafacan.core.domain.model.users.PagedUserResponse
import com.mustafacan.data.network.api.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("all-users")
    suspend fun getAllUsersByPagination(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<ApiResponse<PagedUserResponse>>
}