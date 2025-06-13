package com.mustafacan.data.network.datasource

import com.mustafacan.data.network.api.service.UserService
import com.mustafacan.data.network.util.ApiCallHandler
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val userApi: UserService
) : ApiCallHandler() {

    suspend fun getAllUsersByPagination(page: Int, limit: Int) = apiCall {
        userApi.getAllUsersByPagination(page, limit)
    }
}