package com.mustafacan.core.domain.usecase.api

import androidx.paging.PagingData
import com.mustafacan.core.domain.model.users.User
import com.mustafacan.core.domain.repository.api.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetAllUserPaginatedUseCase  @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(): Flow<PagingData<User>> {
        return repository.getAllUsersPagination()
    }
}