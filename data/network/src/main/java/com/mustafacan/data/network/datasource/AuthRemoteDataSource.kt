package com.mustafacan.data.network.datasource

import com.mustafacan.core.domain.model.auth.LoginRequest
import com.mustafacan.core.domain.model.auth.RegisterRequest
import com.mustafacan.data.network.api.service.AuthService
import com.mustafacan.data.network.util.ApiCallHandler
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(private val authService: AuthService) :
    ApiCallHandler() {

    suspend fun login(request: LoginRequest) = apiCall { authService.login(request) }
    suspend fun register(request: RegisterRequest) = apiCall { authService.register(request) }
}