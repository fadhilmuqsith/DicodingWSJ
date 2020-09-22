package org.d3if4048.thewallstreetjournal.detail

import androidx.lifecycle.ViewModel
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity

class DetailNewsViewModel (private val newsRepository: NewsRepository) : ViewModel() {
    fun setFavoriteNews(news : NewsEntity, newStatus : Boolean) = newsRepository.setFavoriteNews(news , newStatus)
}