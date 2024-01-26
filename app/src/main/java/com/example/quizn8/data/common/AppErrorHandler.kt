package com.example.quizn8.data.common

import android.util.Log.d
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

sealed class AppErrorHandler(open val message: String) {
    data class NetworkErrorHandler(override val message: String) : AppErrorHandler(message)
    data class HttpErrorHandler(override val message: String) : AppErrorHandler(message)
    data class TimeoutErrorHandler(override val message: String) : AppErrorHandler(message)
    data class ServerErrorHandler(override val message: String) : AppErrorHandler(message)
    data class ClientErrorHandler(override val message: String) : AppErrorHandler(message)
    data class UnknownErrorHandler(override val message: String) : AppErrorHandler(message)
    data class InvalidAuthenticationInputs(override val message: String): AppErrorHandler(message)
    data class UserAlreadyExistsErrorHandler(override val message: String): AppErrorHandler(message)

    companion object {
        fun handleThrowable(t: Throwable): AppErrorHandler {
            d("showError", t.toString())
            return when (t) {
                is IOException -> NetworkErrorHandler("Network error occurred: No Internet")
                is HttpException -> {
                    when (t.code()) {
                        in 400..499 -> {
                            ClientErrorHandler(t.response()!!.errorBody().toString())
                        }
                        in 500..599 -> ServerErrorHandler("Server error occurred")
                        else -> HttpErrorHandler("Http error occurred")
                    }
                }
                is TimeoutException -> TimeoutErrorHandler("Can not process task")
                else -> UnknownErrorHandler("An unexpected error occurred")
            }
        }
    }
}