package com.mustafacan.core.domain.repository.datastore

import com.mustafacan.core.model.auth.AuthUser
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {
    fun getLocalUserWithFlow(): Flow<AuthUser?>
    suspend fun getLocalUser(): AuthUser?

    //when login
    suspend fun saveUser(authUser: AuthUser)

    //when logout
    suspend fun clearUser()
}