package com.alican.di

import com.alican.Constants
import com.alican.ExerciseApiService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module

val provideHttpClientModule = module {
    single {
        ExerciseApiService(get())
    }
    single {
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
                    json = Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )

            }
        }
    }
}