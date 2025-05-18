package com.mustafacan.core.domain.repository.datastore

import com.mustafacan.core.domain.model.auth.User
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {
    fun getLocalUserWithFlow(): Flow<User?>
    suspend fun getLocalUser(): User?

    //when login
    suspend fun saveUser(user: User)

    //when logout
    suspend fun clearUser()
}