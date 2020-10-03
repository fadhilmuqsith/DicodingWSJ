package org.d3if4048.thewallstreetjournal.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.d3if4048.thewallstreetjournal.core.data.Resource
import org.d3if4048.thewallstreetjournal.core.domain.model.News
import org.d3if4048.thewallstreetjournal.core.domain.repository.INewsRepository

class NewsInteractor (private val newsRepository: INewsRepository) : NewsUseCase {
    override fun getAllNews(): Flow<Resource<List<News>>> = newsRepository.getAllNews()

    override fun getFavoriteNews(): Flow<List<News>> = newsRepository.getFavoriteNews()

    override fun setFavoriteNews(news: News, state: Boolean)  = newsRepository.setFavoriteNews(news, state)
}