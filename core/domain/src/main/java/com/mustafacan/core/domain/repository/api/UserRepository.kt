package com.mustafacan.core.domain.repository.api

import androidx.paging.PagingData
import com.mustafacan.core.domain.model.users.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
     fun getAllUsersPagination(): Flow<PagingData<User>>
}