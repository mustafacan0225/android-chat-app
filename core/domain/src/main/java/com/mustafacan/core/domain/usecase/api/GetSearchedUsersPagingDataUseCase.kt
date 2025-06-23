package com.mustafacan.core.domain.usecase.api

import androidx.paging.PagingData
import com.mustafacan.core.model.users.UserSearchRequest
import com.mustafacan.core.model.users.User
import com.mustafacan.core.domain.repository.api.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetSearchedUsersPagingDataUseCase  @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(userSearchRequest: UserSearchRequest): Flow<PagingData<User>> {
        return repository.getPaginatedSearchedUsers(userSearchRequest)
    }
}