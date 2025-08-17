package com.mustafacan.core.domain.repository.api

import androidx.paging.PagingData
import com.mustafacan.core.model.chat.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
     fun getPaginatedDirectMessage(senderId: String, receiverId: String): Flow<PagingData<Message>>
}