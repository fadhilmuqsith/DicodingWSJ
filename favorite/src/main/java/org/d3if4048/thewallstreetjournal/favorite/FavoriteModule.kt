package org.d3if4048.thewallstreetjournal.favorite

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {  viewModel { FavoriteViewModel(get()) } }