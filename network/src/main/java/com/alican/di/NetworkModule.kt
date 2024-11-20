package com.alican.di

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
import org.koin.dsl.module

val provideHttpClientModule = module {
    single<HttpClientProvider> { DefaultHttpClientProvider() }
    single {
        ExerciseApiService(get<HttpClientProvider>().provideClient())
    }
}

class DefaultHttpClientProvider : HttpClientProvider {
    override fun provideClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                val sdk = AwesomeSdk.getInstance()
                url(sdk.BASE_URL)
                header("x-rapidapi-key", sdk.API_KEY)
                header("x-rapidapi-host", sdk.API_HOST)
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

interface HttpClientProvider {
    fun provideClient(): HttpClient
}
