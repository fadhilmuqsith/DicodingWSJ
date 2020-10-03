package org.d3if4048.thewallstreetjournal.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.d3if4048.thewallstreetjournal.core.data.Resource
import org.d3if4048.thewallstreetjournal.core.domain.model.News

interface NewsUseCase {
    fun getAllNews(): Flow<Resource<List<News>>>

    fun getFavoriteNews(): Flow<List<News>>

    fun setFavoriteNews(news: News, state:Boolean)
}