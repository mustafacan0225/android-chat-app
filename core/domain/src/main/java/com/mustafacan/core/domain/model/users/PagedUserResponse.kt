package com.mustafacan.core.domain.model.users

import kotlinx.serialization.SerialName

data class PagedUserResponse(
    val users: List<User>,
    val page: Int,
    val totalPages: Int
)

data class User (
    @SerialName("_id")
    val id: String,
    val username: String
)