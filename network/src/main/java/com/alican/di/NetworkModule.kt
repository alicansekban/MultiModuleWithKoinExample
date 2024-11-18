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
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
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

object IsolatedKoinContext {
    private val koinApp: KoinApplication = koinApplication {
        // Boş bir Koin uygulaması başlat
    }

    val koin: Koin
        get() = koinApp.koin

    fun loadModules(modules: List<Module>) {
        koinApp.koin.loadModules(modules)
    }

    fun unloadModules(modules: List<Module>) {
        koinApp.koin.unloadModules(modules)
    }
}


object AwesomeSdk {

    private var isInitialized = false

    fun init() {
        if (!isInitialized) {
            // Koin modüllerini yükle
            IsolatedKoinContext.loadModules(listOf(provideHttpClientModule))
            isInitialized = true
        }
    }

    // Bağımlılıkları kullanıcıya sağla
    fun getExerciseApiService(): ExerciseApiService {
        checkInitialization()
        return IsolatedKoinContext.koin.get()
    }

    private fun checkInitialization() {
        if (!isInitialized) {
            throw IllegalStateException("AwesomeSdk is not initialized. Call AwesomeSdk.init() first.")
        }
    }
}


// example usage of koin isolated context
//object IsolatedKoinContext {
//    val koinApp = koinApplication {
//        // declare used modules
//        modules(provideHttpClientModule)
//    }
//
//    val koin = koinApp.koin
//}
//
//// either we can force users to init koin on sdk lvl
//object AwesomeSdk {
//    fun init() {
//        startKoin {
//            // declare used modules
//            modules(provideHttpClientModule)
//        }
//    }
//
//    fun getExerciseApiService(): ExerciseApiService {
//        return IsolatedKoinContext.koin.get()
//    }
//}
//
//// second example for injecting koin on sdk lvl
//object AwesomeSdk2 {
//
//    fun init() {
//        IsolatedKoinContext.koinApp.koin.loadModules(listOf(provideHttpClientModule))
//    }
//
//    fun getExerciseApiService(): ExerciseApiService {
//        return IsolatedKoinContext.koin.get()
//    }
//}