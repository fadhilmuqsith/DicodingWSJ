package org.d3if4048.thewallstreetjournal.content

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val contenModule = module {
    viewModel { ContentViewModel(get()) }
}