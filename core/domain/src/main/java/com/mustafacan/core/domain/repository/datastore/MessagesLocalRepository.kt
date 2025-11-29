package com.mustafacan.core.domain.repository.datastore


interface MessagesLocalRepository {
    suspend fun hasNewDirectMessage(): Boolean
    suspend fun hasNewGroupMessage(): Boolean
    suspend fun saveHasNewDirectMessage(hasNewMessage: Boolean)
    suspend fun saveHasNewGroupMessage(hasNewMessage: Boolean)
}