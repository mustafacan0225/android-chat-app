package com.mustafacan.core.domain.usecase.api

import com.mustafacan.core.domain.constant.AuthConstants
import com.mustafacan.core.domain.repository.api.AuthRepository
import com.mustafacan.core.domain.usecase.datastore.SaveLocalUserUseCase
import com.mustafacan.core.model.auth.AuthUser
import com.mustafacan.core.model.auth.LoginRequest
import com.mustafacan.core.model.error.BusinessLogicError
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val saveLocalUserUseCase: SaveLocalUserUseCase
) {
    suspend operator fun invoke(request: LoginRequest): Result<AuthUser> {
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

            }
        }
        return result
    }
}