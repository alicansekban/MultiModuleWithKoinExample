package com.alican

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments

class ExerciseApiService(
    private val client: HttpClient
) {

    suspend fun getExerciseList(limit: Int): NetworkResult<List<ExerciseResponseItem>> {
        return safeApiCall<List<ExerciseResponseItem>>(client) {
            url {
                appendPathSegments("exercises")
                parameters.append("limit", limit.toString())
            }
            method = HttpMethod.Get
        }
    }


}