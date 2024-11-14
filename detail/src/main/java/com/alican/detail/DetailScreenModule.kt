package com.alican.detail

import com.alican.repositoryProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val provideModuleForDetail = module {
    viewModel { DetailViewModel(get()) }
    includes(repositoryProvider)
}