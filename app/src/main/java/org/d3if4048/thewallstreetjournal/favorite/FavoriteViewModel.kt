package org.d3if4048.thewallstreetjournal.favorite

import androidx.lifecycle.ViewModel
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository

class FavoriteViewModel(newsRepository: NewsRepository): ViewModel() {
    val favoriteNews = newsRepository.getFavoriteNews()
}