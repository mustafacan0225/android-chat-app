package com.mustafacan.core.domain.usecase.datastore

import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.core.model.auth.AuthUser
import javax.inject.Inject

class SaveLocalUserUseCase @Inject constructor(private val userLocalRepository: UserLocalRepository) {
    suspend operator fun invoke(authUser: AuthUser) {
        userLocalRepository.saveUser(authUser)
    }
}