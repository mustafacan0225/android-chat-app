package com.mustafacan.core.domain.usecase.api

import com.mustafacan.core.domain.constant.AuthConstants
import com.mustafacan.core.domain.error.BusinessLogicError
import com.mustafacan.core.domain.model.auth.LoginRequest
import com.mustafacan.core.domain.model.auth.User
import com.mustafacan.core.domain.repository.api.AuthRepository
import com.mustafacan.core.domain.usecase.datastore.SaveLocalUserUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val saveLocalUserUseCase: SaveLocalUserUseCase
) {
    suspend operator fun invoke(request: LoginRequest): Result<User> {
        if (!request.email.matches(Regex(AuthConstants.EMAIL_REGEX))) {
            return Result.failure(BusinessLogicError.InvalidEmail)
        }

        if (request.password.length < AuthConstants.MIN_PASSWORD_LENGTH) {
            return Result.failure(BusinessLogicError.InvalidPassword)
        }

        val result = repository.login(request)
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