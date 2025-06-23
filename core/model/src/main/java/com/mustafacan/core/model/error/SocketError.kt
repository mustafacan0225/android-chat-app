package com.mustafacan.core.model.error

sealed class SocketError(message: String) : Exception(message) {

    // Auth-related
    object ConnectionError : SocketError("")

}