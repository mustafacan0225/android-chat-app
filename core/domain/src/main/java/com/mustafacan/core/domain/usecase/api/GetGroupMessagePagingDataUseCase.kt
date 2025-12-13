package com.mustafacan.core.domain.usecase.api

import androidx.paging.PagingData
import com.mustafacan.core.domain.repository.api.MessageRepository
import com.mustafacan.core.model.chat.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetGroupMessagePagingDataUseCase  @Inject constructor(private val repository: MessageRepository) {
    operator fun invoke(roomId: String): Flow<PagingData<Message>> {
        return repository.getPaginatedGroupMessage(roomId)
    }
}