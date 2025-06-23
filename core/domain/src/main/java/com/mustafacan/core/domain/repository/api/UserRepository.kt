package com.mustafacan.core.domain.repository.api

import androidx.paging.PagingData
import com.mustafacan.core.model.users.UserSearchRequest
import com.mustafacan.core.model.users.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
     fun getPaginatedAllUsers(): Flow<PagingData<User>>
     fun getPaginatedSearchedUsers(userSearchRequest: UserSearchRequest): Flow<PagingData<User>>
}