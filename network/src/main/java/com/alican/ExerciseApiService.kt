package com.alican

import com.alican.di.provideHttpClientModule
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin

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

object ExampleManuelDependencyInjection {
    private val httpClient: HttpClient by lazy {
        HttpClient {
            defaultRequest {
                url(Constants.BASE_URL)
                header("x-rapidapi-key", Constants.API_KEY)
                header("x-rapidapi-host", Constants.API_HOST)
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    val exerciseApiService: ExerciseApiService by lazy {
        ExerciseApiService(httpClient)
    }
}