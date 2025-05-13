package com.mustafacan.core.domain.error

sealed class BusinessLogicError(message: String) : Exception(message) {

    // Auth-related
    object InvalidEmail : BusinessLogicError("Invalid email")
    object InvalidPassword : BusinessLogicError("Invalid password")
    object InvalidUsername : BusinessLogicError("Invalid username")

}