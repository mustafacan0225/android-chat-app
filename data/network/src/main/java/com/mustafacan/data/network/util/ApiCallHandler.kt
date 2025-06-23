package com.mustafacan.data.network.util

import com.google.gson.Gson
import com.mustafacan.core.model.error.BackendError
import com.mustafacan.core.model.error.CustomNetworkError
import com.mustafacan.data.network.api.model.ApiResponse
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

abstract class ApiCallHandler {

    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<ApiResponse<T>>): Result<T> {
        return try {
            val response = call()
            val body = response.body()

            if (response.isSuccessful && body?.data != null) {
                Result.success(body.data)
            } else {
                val errorBody = response.errorBody()
                val error = errorBody?.let {
                    try {
                        val errorResponse = Gson().fromJson(it.charStream(), ApiResponse::class.java)
                        errorResponse?.error
                    } catch (e: Exception) {
                        null
                    }
                }

                if (error != null) {
                    Result.failure(BackendError.Error(error.message, error.code))
                } else {
                    Result.failure(CustomNetworkError.Default)
                }
            }

        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    private fun mapException(e: Exception): CustomNetworkError {
        return when (e) {
            is SSLHandshakeException -> CustomNetworkError.SSL("Güvenlik sertifikası doğrulanamadı")
            is SocketTimeoutException -> CustomNetworkError.Timeout("İstek zaman aşımına uğradı")
            is ConnectException -> CustomNetworkError.Network("Ağ bağlantısı sağlanamadı")
            else -> CustomNetworkError.Default
        }
    }
}