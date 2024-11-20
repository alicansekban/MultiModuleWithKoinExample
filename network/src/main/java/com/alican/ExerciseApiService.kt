package com.alican

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers

class ExerciseApiService(
    private val client: HttpClient
) {

    suspend fun getExerciseList(limit: Int): NetworkResult<List<ExerciseResponseItem>> =
        safeApiCall(Dispatchers.IO) {
            client.get {
                url {
                    appendPathSegments("exercises", )
                    parameters.append("limit", limit.toString())
                }
            }.body()
        }

}