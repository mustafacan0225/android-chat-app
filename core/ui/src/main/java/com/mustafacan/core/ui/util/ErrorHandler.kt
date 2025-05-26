package com.mustafacan.core.ui.util

import android.content.Context
import com.mustafacan.core.domain.error.BackendError
import com.mustafacan.core.domain.error.BusinessLogicError
import com.mustafacan.core.domain.error.CustomNetworkError
import com.mustafacan.core.domain.error.SocketError
import com.mustafacan.core.ui.R

object ErrorHandler {
    fun resolveErrorMessage(
        context: Context,
        error: Throwable,
    ): String {
        val message = when (error) {
            is BusinessLogicError -> {
                val resId = when (error) {
                    BusinessLogicError.InvalidEmail -> R.string.error_invalid_email
                    BusinessLogicError.InvalidPassword -> R.string.error_invalid_password
                    BusinessLogicError.InvalidUsername -> R.string.error_invalid_username
                    else -> R.string.error_default
                }
                context.getString(resId)
            }

            is CustomNetworkError -> {
                context.getString(R.string.error_network_default)
            }

            is BackendError -> {
                error.message
            }

            is SocketError.ConnectionError -> {
                context.getString(R.string.error_socket_connection)
            }

            else -> context.getString(R.string.error_default)
        }

        return message

    }
}