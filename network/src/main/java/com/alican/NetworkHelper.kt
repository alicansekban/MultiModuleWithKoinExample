package com.alican

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

//suspend fun <T> safeApiCall(
//    dispatcher: CoroutineDispatcher,
//    apiCall: suspend () -> T
//): NetworkResult<T> {
//    return withContext(dispatcher) {
//        try {
//            val response = apiCall.invoke()
//            NetworkResult.Success(response)
//        } catch (throwable: Throwable) {
//            when (throwable) {
//                is ClientRequestException -> { // 4xx Errors
//                    val errorResponse = parseErrorBody(throwable.response)
//                    val errorType = mapStatusCodeToErrorType(throwable.response.status.value)
//                    NetworkResult.GenericError(
//                        code = throwable.response.status.value,
//                        type = errorType,
//                        message = errorResponse?.message
//                    )
//                }
//                is ServerResponseException -> { // 5xx Errors
//                    val errorResponse = parseErrorBody(throwable.response)
//                    NetworkResult.GenericError(
//                        code = throwable.response.status.value,
//                        type = ErrorType.ServerError,
//                        message = errorResponse?.message ?: "Server error occurred"
//                    )
//                }
//                is IOException -> NetworkResult.NetworkError
//                else -> {
//                    NetworkResult.GenericError(
//                        code = -1,
//                        type = ErrorType.Unknown,
//                        message = throwable.message ?: "An unexpected error occurred"
//                    )
//                }
//            }
//        }
//    }
//}

suspend inline fun <reified T> safeApiCall(
    client: HttpClient,
    requestBuilder: HttpRequestBuilder.() -> Unit
): NetworkResult<T> {
    return try {
        val response: HttpResponse = client.request {
            requestBuilder()
        }
        if (response.status.isSuccess()) {
            val responseData: T = response.body()
            NetworkResult.Success(responseData)
        } else {
            val errorBody = response.bodyAsText()
            val errorMessage = parseErrorMessage(errorBody)
            NetworkResult.Error(errorMessage, response.status.value)
        }
    } catch (t: Throwable) {
        when (t) {
            is ClientRequestException -> {
                val errorBody = t.response.bodyAsText()
                val errorMessage = parseErrorMessage(errorBody)
                NetworkResult.Error(errorMessage, t.response.status.value)
            }

            is ServerResponseException -> {
                val errorBody = t.response.bodyAsText()
                val errorMessage = parseErrorMessage(errorBody)
                NetworkResult.Error(errorMessage, t.response.status.value)
            }

            is IOException -> NetworkResult.Error("Network error: ${t.message}")
            else -> NetworkResult.Error("An unexpected error occurred: ${t.message}")
        }

    }
}

fun parseErrorMessage(errorBody: String): String? {
    return try {
        val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody)
        errorResponse.message
    } catch (e: JsonConvertException) {
        "An unexpected error occurred"
    }
}

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String? = null, val statusCode: Int? = null) :
        NetworkResult<Nothing>()
}
