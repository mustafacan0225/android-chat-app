package com.mustafacan.core.domain.error

sealed class SocketError(message: String) : Exception(message) {

    // Auth-related
    object ConnectionError : SocketError("")

}