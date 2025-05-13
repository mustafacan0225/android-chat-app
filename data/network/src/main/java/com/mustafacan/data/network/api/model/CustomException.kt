package com.mustafacan.data.network.api.model

sealed class CustomException(
    override val message: String,
    open val code: Int? = null
) : Exception(message) {

    data class ApiError(
        override val message: String,
        override val code: Int? = null
    ) : CustomException(message, code)

    data class SSL(
        override val message: String
    ) : CustomException(message)

    data class Timeout(
        override val message: String
    ) : CustomException(message)

    data class Network(
        override val message: String
    ) : CustomException(message)

    object Default : CustomException("İşlem gerçekleştirilemedi, daha sonra tekrar deneyiniz!")
}