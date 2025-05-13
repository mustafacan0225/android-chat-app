package com.mustafacan.data.network.util

import com.google.gson.Gson
import com.mustafacan.data.network.api.model.ApiResponse
import com.mustafacan.data.network.api.model.CustomException
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
                    Result.failure(CustomException.ApiError(error.message, error.code))
                } else {
                    Result.failure(CustomException.Default)
                }
            }

        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    private fun mapException(e: Exception): CustomException {
        return when (e) {
            is CustomException -> e
            is SSLHandshakeException -> CustomException.SSL("Güvenlik sertifikası doğrulanamadı")
            is SocketTimeoutException -> CustomException.Timeout("İstek zaman aşımına uğradı")
            is ConnectException -> CustomException.Network("Ağ bağlantısı sağlanamadı")
            else -> CustomException.Default
        }
    }
}