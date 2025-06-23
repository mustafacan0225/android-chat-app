package com.mustafacan.core.domain.repository.api

import com.mustafacan.core.model.auth.AuthUser
import com.mustafacan.core.model.auth.LoginRequest
import com.mustafacan.core.model.auth.RegisterRequest

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<AuthUser>
    suspend fun register(request: RegisterRequest): Result<AuthUser>
}