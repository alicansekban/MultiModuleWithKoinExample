package com.alican

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): NetworkResult<T> {
    return withContext(dispatcher) {
        try {
            NetworkResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is kotlinx.io.IOException -> {
                    NetworkResult.GenericError()
                }

                else -> {
                    NetworkResult.GenericError()
                }
            }
        }
    }
}

sealed class NetworkResult<out T> {
    data class Success<out T>(val value: T) : NetworkResult<T>()
    data class GenericError(val code: Int? = null, val error: String? = null) :
        NetworkResult<Nothing>()
}

