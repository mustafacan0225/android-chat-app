package com.mustafacan.data.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mustafacan.core.domain.repository.api.MessageRepository
import com.mustafacan.core.model.chat.Message
import com.mustafacan.data.network.datasource.MessagesRemoteDataSource
import com.mustafacan.data.network.pagination.MessagesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val remoteDataSource: MessagesRemoteDataSource) :
    MessageRepository {
    override fun getPaginatedDirectMessage(
        senderId: String,
        receiverId: String
    ): Flow<PagingData<Message>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5,
                enablePlaceholders = false,

            ),
            pagingSourceFactory = {
                MessagesPagingSource(senderId, receiverId, remoteDataSource)
            }
        ).flow
    }


}
