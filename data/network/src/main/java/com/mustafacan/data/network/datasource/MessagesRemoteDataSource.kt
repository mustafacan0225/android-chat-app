package com.mustafacan.data.network.datasource

import com.mustafacan.data.network.api.service.MessageService
import com.mustafacan.data.network.util.ApiCallHandler
import javax.inject.Inject

class MessagesRemoteDataSource @Inject constructor(
    private val messagesApi: MessageService
) : ApiCallHandler() {

    suspend fun getPaginatedDirectMessages(senderId: String, receiverId: String, beforeId: String? = null) = apiCall {
        messagesApi.getPaginatedDirectMessages(senderId, receiverId, beforeId)
    }

    suspend fun getPaginatedGroupMessages(roomId: String, beforeId: String? = null) = apiCall {
        messagesApi.getPaginatedGroupMessages(roomId, beforeId)
    }

}