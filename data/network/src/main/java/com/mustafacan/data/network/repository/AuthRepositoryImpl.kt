package com.mustafacan.data.network.repository

import com.mustafacan.core.domain.repository.api.AuthRepository
import com.mustafacan.core.model.auth.AuthUser
import com.mustafacan.core.model.auth.LoginRequest
import com.mustafacan.core.model.auth.RegisterRequest
import com.mustafacan.data.network.datasource.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val remoteDataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun login(request: LoginRequest): Result<AuthUser> {
        return remoteDataSource.login(request)
    }

    override suspend fun register(request: RegisterRequest): Result<AuthUser> {
        return remoteDataSource.register(request)
    }

}