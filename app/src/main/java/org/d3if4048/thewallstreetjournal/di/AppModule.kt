package org.d3if4048.thewallstreetjournal.di

import org.d3if4048.thewallstreetjournal.core.domain.usecase.NewsInteractor
import org.d3if4048.thewallstreetjournal.core.domain.usecase.NewsUseCase
import org.d3if4048.thewallstreetjournal.detail.DetailNewsViewModel
import org.d3if4048.thewallstreetjournal.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailNewsViewModel(get()) }
}