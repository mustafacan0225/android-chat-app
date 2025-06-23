package com.mustafacan.core.model.error

sealed class BusinessLogicError(message: String) : Exception(message) {

    // Auth-related
    object InvalidEmail : BusinessLogicError("")
    object InvalidPassword : BusinessLogicError("")
    object InvalidUsername : BusinessLogicError("")

}