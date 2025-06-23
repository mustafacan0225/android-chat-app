package com.mustafacan.data.network.datasource

import com.mustafacan.core.model.users.UserSearchRequest
import com.mustafacan.data.network.api.service.UserService
import com.mustafacan.data.network.util.ApiCallHandler
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val userApi: UserService
) : ApiCallHandler() {

    suspend fun getPaginatedAllUsers(page: Int, limit: Int) = apiCall {
        userApi.getPaginatedAllUsers(page, limit)
    }

    suspend fun getPaginatedSearchedUsers(page: Int, limit: Int, userSearchRequest: UserSearchRequest) = apiCall {
        userApi.getPaginatedSearchedUsers(page, limit, userSearchRequest)
    }
}