package com.alican

import com.alican.di.AwesomeSdk
import com.alican.di.provideHttpClientModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

val repositoryProvider = module {
    single { ExerciseRepository(AwesomeSdk.getExerciseApiService()) }
    //includes(provideHttpClientModule)
}