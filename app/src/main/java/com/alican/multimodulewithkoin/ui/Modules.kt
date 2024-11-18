package com.alican.multimodulewithkoin.ui

import com.alican.di.AwesomeSdk
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val allModules = module {
//    includes(provideModuleForHome)
//    includes(provideModuleForDetail)
    viewModel { HomeViewModel() }
}