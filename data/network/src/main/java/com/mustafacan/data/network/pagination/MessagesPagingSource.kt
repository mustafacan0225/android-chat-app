package com.mustafacan.data.network.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mustafacan.core.model.chat.Message
import com.mustafacan.data.network.datasource.MessagesRemoteDataSource
import kotlinx.coroutines.delay

class MessagesPagingSource(
    private val senderId: String,
    private val receiverId: String,
    private val remoteDataSource: MessagesRemoteDataSource
) : PagingSource<String, Message>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Message> {
        val beforeId = params.key

        if (beforeId == null)
            delay(2000)

        val result = remoteDataSource.getPaginatedDirectMessages(
            senderId = senderId,
            receiverId = receiverId,
            beforeId = beforeId
        )

        return if (result.isSuccess) {
            val data = result.getOrNull()
            if (data != null) {
                val messages = data.messages

                LoadResult.Page(
                    data = messages,
                    prevKey = if (data.hasMore) data.lastId else null,
                    nextKey = null
                )
            } else {
                LoadResult.Error(Throwable("Empty response"))
            }
        } else {
            LoadResult.Error(result.exceptionOrNull() ?: Throwable("Unknown error"))
        }
    }

    override fun getRefreshKey(state: PagingState<String, Message>): String? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey
        }
    }
}