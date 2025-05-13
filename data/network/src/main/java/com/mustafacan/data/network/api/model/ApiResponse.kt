package com.mustafacan.data.network.api.model

data class ApiResponse<T>(
    val data: T? = null,
    val error: ApiError? = null
)