package com.mustafacan.core.domain.usecase.datastore

import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.core.model.auth.AuthUser
import javax.inject.Inject

class GetLocalUserUseCase @Inject constructor(private val userLocalRepository: UserLocalRepository) {
    suspend operator fun invoke(): AuthUser? {
        return userLocalRepository.getLocalUser()
    }
}