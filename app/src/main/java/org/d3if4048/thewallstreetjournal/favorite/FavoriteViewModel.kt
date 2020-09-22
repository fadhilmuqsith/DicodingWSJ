package org.d3if4048.thewallstreetjournal.favorite

import androidx.lifecycle.ViewModel
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository
import org.d3if4048.thewallstreetjournal.core.domain.usecase.NewsUseCase

class FavoriteViewModel(newsUseCase: NewsUseCase): ViewModel() {
    val favoriteNews = newsUseCase.getFavoriteNews()
}