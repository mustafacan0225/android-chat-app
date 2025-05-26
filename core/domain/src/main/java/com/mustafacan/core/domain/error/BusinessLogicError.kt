package com.mustafacan.core.domain.error

sealed class BusinessLogicError(message: String) : Exception(message) {

    // Auth-related
    object InvalidEmail : BusinessLogicError("")
    object InvalidPassword : BusinessLogicError("")
    object InvalidUsername : BusinessLogicError("")

}