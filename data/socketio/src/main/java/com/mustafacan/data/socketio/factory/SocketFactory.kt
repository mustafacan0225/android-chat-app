package com.mustafacan.data.socketio.factory

import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.data.socketio.BuildConfig
import io.socket.client.IO
import io.socket.client.Socket
import javax.inject.Inject

class SocketFactory @Inject constructor(private val userLocalRepository: UserLocalRepository) {

    suspend fun create(): Socket {
        val user = userLocalRepository.getLocalUser()
        if (user == null)
            throw IllegalArgumentException("user is required for socket connection")

        val opts = IO.Options()
        opts.auth = mapOf("userId" to user.id)
        return IO.socket(BuildConfig.SOCKET_URL, opts)
    }
}