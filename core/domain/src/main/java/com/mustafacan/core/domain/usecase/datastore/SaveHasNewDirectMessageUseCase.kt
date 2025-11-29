package com.mustafacan.core.domain.usecase.datastore

import com.mustafacan.core.domain.repository.datastore.MessagesLocalRepository
import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.core.model.auth.AuthUser
import javax.inject.Inject

class SaveHasNewDirectMessageUseCase @Inject constructor(private val messagesLocalRepository: MessagesLocalRepository) {
    suspend operator fun invoke(hasNewMessage: Boolean) {
        messagesLocalRepository.saveHasNewDirectMessage(hasNewMessage)
    }
}