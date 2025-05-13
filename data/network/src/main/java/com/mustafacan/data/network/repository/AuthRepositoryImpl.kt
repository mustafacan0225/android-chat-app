package com.mustafacan.data.network.repository

import com.mustafacan.core.domain.model.auth.LoginRequest
import com.mustafacan.core.domain.model.auth.RegisterRequest
import com.mustafacan.core.domain.model.auth.User
import com.mustafacan.core.domain.repository.api.AuthRepository
import com.mustafacan.data.network.datasource.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val remoteDataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun login(request: LoginRequest): Result<User> {
        return remoteDataSource.login(request)
    }

    override suspend fun register(request: RegisterRequest): Result<User> {
        return remoteDataSource.register(request)
    }

}