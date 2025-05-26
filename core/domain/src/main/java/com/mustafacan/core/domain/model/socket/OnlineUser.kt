package com.mustafacan.core.domain.model.socket

import kotlinx.serialization.Serializable

@Serializable
data class OnlineUser(
    val id: String,
    val name: String
)
