package org.d3if4048.thewallstreetjournal.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository
import org.d3if4048.thewallstreetjournal.core.domain.usecase.NewsUseCase

class HomeViewModel(newsUseCase: NewsUseCase) : ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()
}