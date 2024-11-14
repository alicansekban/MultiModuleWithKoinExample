package com.alican

import org.koin.dsl.module
import com.alican.home.HomeViewModel
import org.koin.core.module.dsl.viewModel

val provideModuleForHome = module {
    viewModel { HomeViewModel(get()) }
    includes(repositoryProvider)
}