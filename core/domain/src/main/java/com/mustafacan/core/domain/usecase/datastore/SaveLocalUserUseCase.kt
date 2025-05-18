package com.mustafacan.core.domain.usecase.datastore

import com.mustafacan.core.domain.model.auth.User
import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import javax.inject.Inject

class SaveLocalUserUseCase @Inject constructor(private val userLocalRepository: UserLocalRepository) {
    suspend operator fun invoke(user: User) {
        userLocalRepository.saveUser(user)
    }
}