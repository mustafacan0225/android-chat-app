package com.mustafacan.data.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mustafacan.core.model.users.UserSearchRequest
import com.mustafacan.core.model.users.User
import com.mustafacan.core.domain.repository.api.UserRepository
import com.mustafacan.data.network.datasource.UsersRemoteDataSource
import com.mustafacan.data.network.pagination.SearchedUsersPagingSource
import com.mustafacan.data.network.pagination.UsersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val remoteDataSource: UsersRemoteDataSource) :
    UserRepository {
    override fun getPaginatedAllUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                initialLoadSize = 15,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(remoteDataSource) }
        ).flow
    }

    override fun getPaginatedSearchedUsers(userSearchRequest: UserSearchRequest): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                initialLoadSize = 15,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchedUsersPagingSource(remoteDataSource, userSearchRequest) }
        ).flow
    }


}
