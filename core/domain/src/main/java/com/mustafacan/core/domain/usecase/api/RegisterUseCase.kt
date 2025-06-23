package com.mustafacan.core.domain.usecase.api

import com.mustafacan.core.domain.constant.AuthConstants
import com.mustafacan.core.domain.repository.api.AuthRepository
import com.mustafacan.core.domain.usecase.datastore.SaveLocalUserUseCase
import com.mustafacan.core.model.auth.AuthUser
import com.mustafacan.core.model.auth.RegisterRequest
import com.mustafacan.core.model.error.BusinessLogicError
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val saveLocalUserUseCase: SaveLocalUserUseCase
) {
    suspend operator fun invoke(request: RegisterRequest): Result<AuthUser> {

        if (request.username.length < AuthConstants.MIN_USERNAME_LENGTH) {
            return Result.failure(BusinessLogicError.InvalidUsername)
        }

        if (!request.email.matches(Regex(AuthConstants.EMAIL_REGEX))) {
            return Result.failure(BusinessLogicError.InvalidEmail)
        }

        if (request.password.length < AuthConstants.MIN_PASSWORD_LENGTH) {
            return Result.failure(BusinessLogicError.InvalidPassword)
        }

        val result = repository.register(request)
        if (result.isSuccess) {
            try {
                saveLocalUserUseCase(result.getOrThrow())
            } catch (e: Exception) {
                println("Local user save error: ${e.message}")
            }
        }
        return result
    }
}