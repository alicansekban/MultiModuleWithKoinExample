package com.alican

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            val response = apiCall.invoke()
            NetworkResult.Success(response)
        } catch (throwable: Throwable) {
            when (throwable) {
                is ClientRequestException -> { // 4xx Errors
                    val errorResponse = parseErrorBody(throwable.response)
                    val errorType = mapStatusCodeToErrorType(throwable.response.status.value)
                    NetworkResult.GenericError(
                        code = throwable.response.status.value,
                        type = errorType,
                        message = errorResponse?.message
                    )
                }
                is ServerResponseException -> { // 5xx Errors
                    val errorResponse = parseErrorBody(throwable.response)
                    NetworkResult.GenericError(
                        code = throwable.response.status.value,
                        type = ErrorType.ServerError,
                        message = errorResponse?.message ?: "Server error occurred"
                    )
                }
                is IOException -> NetworkResult.NetworkError
                else -> {
                    NetworkResult.GenericError(
                        code = -1,
                        type = ErrorType.Unknown,
                        message = throwable.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }
}

private fun mapStatusCodeToErrorType(statusCode: Int): ErrorType {
    return when (statusCode) {
        400 -> ErrorType.BadRequest
        401 -> ErrorType.Unauthorized
        403 -> ErrorType.Forbidden
        404 -> ErrorType.NotFound
        in 500..599 -> ErrorType.ServerError
        else -> ErrorType.Unknown
    }
}
private suspend fun parseErrorBody(response: HttpResponse): ErrorResponse? {
    return try {
        val errorBody = response.bodyAsText()
        Json.decodeFromString<ErrorResponse>(errorBody)
    } catch (e: Exception) {
        null // return null if error parsing the error body
    }
}


sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()
    data class GenericError(val code: Int, val type: ErrorType, val message: String?,) : NetworkResult<Nothing>()
    data object NetworkError : NetworkResult<Nothing>()
}

enum class ErrorType {
    NotFound, // 404
    Unauthorized, // 401
    Forbidden, // 403
    ServerError, // 5xx
    BadRequest, // 400
    Unknown // Others
}

