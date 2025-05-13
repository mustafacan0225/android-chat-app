package com.mustafacan.data.network.api.service

import com.mustafacan.core.domain.model.auth.LoginRequest
import com.mustafacan.core.domain.model.auth.RegisterRequest
import com.mustafacan.core.domain.model.auth.User
import com.mustafacan.data.network.api.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<User>>

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<ApiResponse<User>>
}