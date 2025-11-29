package com.mustafacan.data.network.repository

import com.mustafacan.core.domain.repository.api.RoomRepository
import com.mustafacan.core.model.room.DirectMessageRoomsRequestModel
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import com.mustafacan.core.model.room.GroupMessageRoomsResponseModel
import com.mustafacan.data.network.datasource.RoomsRemoteDataSource
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(private val remoteDataSource: RoomsRemoteDataSource) :
    RoomRepository {
    override suspend fun getDirectMessageRooms(request: DirectMessageRoomsRequestModel): Result<List<DirectMessageRoomsResponseModel>> {
        return remoteDataSource.getDirectMessageRooms(request)
    }

    override suspend fun getGroupMessageRooms(): Result<List<GroupMessageRoomsResponseModel>> {
        return remoteDataSource.getGroupMessageRooms()
    }
}