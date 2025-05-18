package com.mustafacan.data.datastore.repository

import androidx.datastore.core.DataStore
import com.mustafacan.core.domain.model.auth.User
import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.data.datastore.manager.PreferencesDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UserLocalRepositoryImpl @Inject constructor(
    private val dataStoreManager: PreferencesDataStoreManager
) : UserLocalRepository {

    companion object {
        private const val USER_KEY = "user_key"
    }

    override fun getLocalUserWithFlow(): Flow<User?> =
        dataStoreManager.getDataFlow(USER_KEY, User::class.java)

    override suspend fun getLocalUser(): User? =
        dataStoreManager.getData(USER_KEY, User::class.java)

    override suspend fun saveUser(user: User) =
        dataStoreManager.saveData(USER_KEY, user, User::class.java)

    override suspend fun clearUser() =
        dataStoreManager.clearData(USER_KEY)
}