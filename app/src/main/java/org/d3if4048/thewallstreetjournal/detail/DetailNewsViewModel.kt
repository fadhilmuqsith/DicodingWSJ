package org.d3if4048.thewallstreetjournal.detail

import androidx.lifecycle.ViewModel
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity
import org.d3if4048.thewallstreetjournal.core.domain.model.News
import org.d3if4048.thewallstreetjournal.core.domain.usecase.NewsUseCase

class DetailNewsViewModel (private val newsUseCase: NewsUseCase) : ViewModel() {
    fun setFavoriteNews(news : News, newStatus : Boolean) = newsUseCase.setFavoriteNews(news , newStatus)
}