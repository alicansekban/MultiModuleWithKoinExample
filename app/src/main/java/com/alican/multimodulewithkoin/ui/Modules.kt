package com.alican.multimodulewithkoin.ui

import com.alican.detail.provideModuleForDetail
import com.alican.provideModuleForHome
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.compose.getKoin
import org.koin.core.context.GlobalContext.get
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val allModules = module {
    includes(provideModuleForHome)
    includes(provideModuleForDetail)
}