package com.mustafacan.core.domain.error

sealed class BackendError(
    override val message: String,
    open val code: Int? = null
) : Exception(message) {
    data class Error(
        override val message: String,
        override val code: Int? = null
    ) : BackendError(message, code)

}