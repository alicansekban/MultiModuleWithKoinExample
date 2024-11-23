package com.alican.di

import android.content.Context
import com.alican.ExerciseApiService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.api.ClientPlugin
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module

val provideHttpClientModule = module {
    single<HttpClientProvider> { DefaultHttpClientProvider() }
    single { ExerciseApiService(get<HttpClientProvider>().provideClient()) }
}

class DefaultHttpClientProvider : HttpClientProvider, KoinComponent {
    private val sdk = AwesomeSdk.getInstance()
    private val context: Context by inject()
    override fun provideClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                url(sdk.BASE_URL)
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
            install(createCustomPlugin(context))
        }
    }
}

interface HttpClientProvider {
    fun provideClient(): HttpClient
}

fun createCustomPlugin(
    context: Context
): ClientPlugin<Unit> {
    val sdk = AwesomeSdk.getInstance()
    val dataStoreHelper = DataStoreHelper(context)

    return createClientPlugin("CustomHeaderPlugin") {
        onRequest { request, _ ->

            val token = runBlocking {
                dataStoreHelper.readValue()
            }
            request.headers.append("x-rapidapi-key", sdk.API_KEY)
            request.headers.append("x-rapidapi-host", sdk.API_HOST)
            token?.let {
                request.headers.append("Authorization", "Bearer $it")
            }
        }
    }
}