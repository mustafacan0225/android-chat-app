package com.mustafacan.core.domain.usecase.api

import com.mustafacan.core.domain.repository.api.RoomRepository
import com.mustafacan.core.model.room.DirectMessageRoomsRequestModel
import com.mustafacan.core.model.room.DirectMessageRoomsResponseModel
import javax.inject.Inject

class GetDirectMessageRoomsUseCase @Inject constructor(
    private val repository: RoomRepository,
) {
    suspend operator fun invoke(request: DirectMessageRoomsRequestModel): Result<List<DirectMessageRoomsResponseModel>> {
        return repository.getDirectMessageRooms(request)
    }
}