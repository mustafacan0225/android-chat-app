package com.mustafacan.core.domain.usecase.datastore

import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import javax.inject.Inject

class DeleteLocalUserUseCase @Inject constructor(private val userLocalRepository: UserLocalRepository) {
    suspend operator fun invoke() {
        userLocalRepository.clearUser()
    }
}