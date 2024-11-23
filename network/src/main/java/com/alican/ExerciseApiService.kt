package com.alican

import com.alican.di.DataStoreHelper
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


interface ExerciseApiServiceImp {
    suspend fun getExerciseList(limit: Int): NetworkResult<List<ExerciseResponseItem>>

    suspend fun getToken(): NetworkResult<String>
}


class ExerciseApiService(
    private val client: HttpClient,
) : ExerciseApiServiceImp, KoinComponent {
    private val dataStoreHelper: DataStoreHelper by inject()
    override suspend fun getExerciseList(limit: Int): NetworkResult<List<ExerciseResponseItem>> {
        return safeApiCall<List<ExerciseResponseItem>>(client) {
            url {
                appendPathSegments("exercises")
                parameters.append("limit", limit.toString())
            }
            method = HttpMethod.Get
        }
    }

    override suspend fun getToken(): NetworkResult<String> {
        val result = safeApiCall<String>(client) {
            url {
                appendPathSegments("auth/login")
            }
            method = HttpMethod.Post
        }

        if (result is NetworkResult.Success) {
            val token = result.data
            dataStoreHelper.writeValue(value = token)
        }

        return result
    }

}