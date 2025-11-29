package com.mustafacan.data.datastore.repository

import com.mustafacan.core.domain.repository.datastore.MessagesLocalRepository
import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.core.model.auth.AuthUser
import com.mustafacan.data.datastore.manager.PreferencesDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MessagesLocalRepositoryImpl @Inject constructor(
    private val dataStoreManager: PreferencesDataStoreManager
) : MessagesLocalRepository {

    companion object {
        private const val DIRECT_MESSAGE_KEY = "has_new_direct_message"
        private const val GROUP_MESSAGE_KEY = "has_new_group_message"
    }

    override suspend fun hasNewDirectMessage() =
        dataStoreManager.getData(DIRECT_MESSAGE_KEY, Boolean::class.java) ?: false

    override suspend fun hasNewGroupMessage() =
        dataStoreManager.getData(GROUP_MESSAGE_KEY, Boolean::class.java) ?: false

    override suspend fun saveHasNewDirectMessage(hasNewMessage: Boolean) =
        dataStoreManager.saveData(DIRECT_MESSAGE_KEY, hasNewMessage, Boolean::class.java)

    override suspend fun saveHasNewGroupMessage(hasNewMessage: Boolean) =
        dataStoreManager.saveData(GROUP_MESSAGE_KEY, hasNewMessage, Boolean::class.java)

}