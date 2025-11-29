package com.mustafacan.core.domain.usecase.datastore

import com.mustafacan.core.domain.repository.datastore.MessagesLocalRepository
import javax.inject.Inject

class GetHasNewDirectMessageUseCase @Inject constructor(private val messagesLocalRepository: MessagesLocalRepository) {
    suspend operator fun invoke(): Boolean {
        return messagesLocalRepository.hasNewDirectMessage()
    }
}