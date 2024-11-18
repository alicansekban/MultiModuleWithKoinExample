package com.alican.multimodulewithkoin

import android.app.Application
import com.alican.di.AwesomeSdk
import com.alican.multimodulewithkoin.ui.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(allModules)
           // AwesomeSdk.init()
        }
    }
}