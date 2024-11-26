package com.alican.multimodulewithkoin.ui

import com.alican.multimodulewithkoin.ui.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val allModules = module {
//    includes(provideModuleForHome)
//    includes(provideModuleForDetail)
    viewModel { HomeViewModel() }
}