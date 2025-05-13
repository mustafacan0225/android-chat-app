package com.mustafacan.core.domain.repository.api

import com.mustafacan.core.domain.model.auth.LoginRequest
import com.mustafacan.core.domain.model.auth.RegisterRequest
import com.mustafacan.core.domain.model.auth.User

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<User>
    suspend fun register(request: RegisterRequest): Result<User>
}