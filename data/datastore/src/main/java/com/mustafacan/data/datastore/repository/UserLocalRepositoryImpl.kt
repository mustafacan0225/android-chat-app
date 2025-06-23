package com.mustafacan.data.datastore.repository

import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.core.model.auth.AuthUser
import com.mustafacan.data.datastore.manager.PreferencesDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UserLocalRepositoryImpl @Inject constructor(
    private val dataStoreManager: PreferencesDataStoreManager
) : UserLocalRepository {

    companion object {
        private const val USER_KEY = "user_key"
    }

    override fun getLocalUserWithFlow(): Flow<AuthUser?> =
        dataStoreManager.getDataFlow(USER_KEY, AuthUser::class.java)

    override suspend fun getLocalUser(): AuthUser? =
        dataStoreManager.getData(USER_KEY, AuthUser::class.java)

    override suspend fun saveUser(authUser: AuthUser) =
        dataStoreManager.saveData(USER_KEY, authUser, AuthUser::class.java)

    override suspend fun clearUser() =
        dataStoreManager.clearData(USER_KEY)
}