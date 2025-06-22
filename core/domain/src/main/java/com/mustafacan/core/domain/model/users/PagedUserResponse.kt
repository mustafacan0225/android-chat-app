package com.mustafacan.core.domain.model.users

import com.google.gson.annotations.SerializedName

data class PagedUserResponse(
    val users: List<User>,
    val page: Int,
    val totalPages: Int
)

data class User (
    @SerializedName("_id")
    val id: String,
    val username: String
)