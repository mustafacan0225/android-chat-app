package com.mustafacan.core.domain.usecase.api

import com.mustafacan.core.domain.repository.api.RoomRepository
import com.mustafacan.core.model.room.GroupMessageRoomsResponseModel
import javax.inject.Inject

class GetGroupMessageRoomsUseCase @Inject constructor(
    private val repository: RoomRepository,
) {
    suspend operator fun invoke(): Result<List<GroupMessageRoomsResponseModel>> {
        return repository.getGroupMessageRooms()
    }
}