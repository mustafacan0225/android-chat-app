package com.mustafacan.core.domain.usecase.api

import androidx.paging.PagingData
import com.mustafacan.core.domain.repository.api.MessageRepository
import com.mustafacan.core.model.chat.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDirectMessagePagingDataUseCase  @Inject constructor(private val repository: MessageRepository) {
    operator fun invoke(senderId: String, receiverId: String): Flow<PagingData<Message>> {
        return repository.getPaginatedDirectMessage(senderId, receiverId)
    }
}