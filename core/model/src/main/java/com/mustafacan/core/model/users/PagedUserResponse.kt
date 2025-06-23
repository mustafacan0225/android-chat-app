package com.mustafacan.core.model.users

data class PagedUserResponse(
    val users: List<User>,
    val page: Int,
    val totalPages: Int
)

