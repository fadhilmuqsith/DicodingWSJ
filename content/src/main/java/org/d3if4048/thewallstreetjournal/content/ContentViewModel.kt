package org.d3if4048.thewallstreetjournal.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import org.d3if4048.thewallstreetjournal.core.domain.usecase.NewsUseCase

class ContentViewModel (newsUseCase: NewsUseCase) : ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()
}