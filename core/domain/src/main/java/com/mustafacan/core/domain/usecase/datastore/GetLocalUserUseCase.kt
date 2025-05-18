package com.mustafacan.core.domain.usecase.datastore

import com.mustafacan.core.domain.model.auth.User
import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import javax.inject.Inject

class GetLocalUserUseCase @Inject constructor(private val userLocalRepository: UserLocalRepository) {
    suspend operator fun invoke(): User? {
        return userLocalRepository.getLocalUser()
    }
}