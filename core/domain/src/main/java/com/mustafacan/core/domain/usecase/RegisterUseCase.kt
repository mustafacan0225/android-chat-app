package com.mustafacan.core.domain.usecase

import com.mustafacan.core.domain.constant.AuthConstants
import com.mustafacan.core.domain.error.BusinessLogicError
import com.mustafacan.core.domain.model.auth.RegisterRequest
import com.mustafacan.core.domain.model.auth.User
import com.mustafacan.core.domain.repository.api.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(request: RegisterRequest): Result<User> {

        if (request.username.length < AuthConstants.MIN_USERNAME_LENGTH) {
            return Result.failure(BusinessLogicError.InvalidUsername)
        }

        if (!request.email.matches(Regex(AuthConstants.EMAIL_REGEX))) {
            return Result.failure(BusinessLogicError.InvalidEmail)
        }

        if (request.password.length < AuthConstants.MIN_PASSWORD_LENGTH) {
            return Result.failure(BusinessLogicError.InvalidPassword)
        }

        return repository.register(request)
    }
}