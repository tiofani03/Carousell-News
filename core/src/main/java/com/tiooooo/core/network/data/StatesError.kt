package com.tiooooo.core.network.data

import com.google.gson.Gson
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

/** Error type when no internet connection. */
object NoInternetError : States.Error(message = "No Internet")

/** Error type when connection is time out. */
object TimeOutError : States.Error(message = "Time Out")

/** Error type when API response is not 2xx code. */
data class HttpError(
    override val message: String,
    val messageTitle: String,
    val code: Int,
    val data: Any?,
) : States.Error(message = message)

/** [data class/object] WhateverError : States.Error(message = ..., meta = ...) */
fun Exception.toError(): States.Error {
    val defaultErrorMsg =
        "Unable to connect with server at the moment. Please try again later."
    return try {
        when {
            this is IOException && message == "No Internet" -> {
                NoInternetError
            }

            this is HttpException -> {
                val error = Gson().fromJson(
                    response()?.errorBody()?.string().orEmpty(),
                    RawHttpError::class.java,
                )
                HttpError(
                    message = error.message ?: message(),
                    messageTitle = error.messageTitle.orEmpty(),
                    code = error.code ?: code(),
                    data = error.data,
                )
            }

            this is SocketTimeoutException -> {
                TimeOutError
            }

            this is UnknownHostException -> {
                NoInternetError
            }
            else -> {
                States.Error(message = defaultErrorMsg)
            }
        }
    } catch (e: Exception) {
        States.Error(message = defaultErrorMsg)
    }
}
