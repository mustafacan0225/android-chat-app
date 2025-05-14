package com.mustafacan.core.domain.error

sealed class CustomNetworkError(
    override val message: String,
) : Exception(message) {

    data class SSL(
        override val message: String
    ) : CustomNetworkError(message)

    data class Timeout(
        override val message: String
    ) : CustomNetworkError(message)

    data class Network(
        override val message: String
    ) : CustomNetworkError(message)

    object Default : CustomNetworkError("")
}