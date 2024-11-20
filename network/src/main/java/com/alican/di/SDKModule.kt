package com.alican.di

import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import java.util.concurrent.atomic.AtomicReference


class AwesomeSdk private constructor() {
    var BASE_URL = "https://exercisedb.p.rapidapi.com/"
    var API_HOST = "exercisedb.p.rapidapi.com"
    var API_KEY = "3511ffea98msh5c7624fcbf7920ap1c6b76jsn0693938b306c"

    private var isInitialized = false

    init {
        initializeKoin()
    }

    fun setIdentity(
        baseUrl: String,
        host: String,
        apiKey: String
    ) {
        BASE_URL = baseUrl
        API_HOST = host
        API_KEY = apiKey


    }

    private fun initializeKoin() {
        if (!isInitialized) {
            IsolatedKoinContext.getInstance().loadModules(listOf(provideHttpClientModule))
            isInitialized = true
        }
    }

    inline fun <reified T> get(): T {
        return IsolatedKoinContext.getInstance().koin.get()
    }

    fun close() {
        IsolatedKoinContext.getInstance().close()
        isInitialized = false
    }

    companion object {
        private val selfInstance = AtomicReference<AwesomeSdk?>(null)

        fun getInstance(): AwesomeSdk {
            return selfInstance.get() ?: synchronized(this) {
                selfInstance.get() ?: AwesomeSdk().also { selfInstance.set(it) }
            }
        }
    }
}

class IsolatedKoinContext private constructor() {
    private val koinApp: KoinApplication by lazy {
        koinApplication {

        }
    }

    val koin: Koin
        get() = koinApp.koin

    fun loadModules(modules: List<Module>) {
        koinApp.koin.loadModules(modules)
    }

    fun unloadModules(modules: List<Module>) {
        koinApp.koin.unloadModules(modules)
    }

    fun close() {
        koinApp.close()
    }

    companion object {
        private val selfInstance = AtomicReference<IsolatedKoinContext?>(null)

        fun getInstance(): IsolatedKoinContext {
            //  using atomic to avoid multi-thread issues
            return selfInstance.get() ?: synchronized(this) {
                selfInstance.get() ?: IsolatedKoinContext().also {
                    selfInstance.set(it)
                }
            }
        }
    }
}