package org.d3if4048.thewallstreetjournal.core.data.source.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity
import org.d3if4048.thewallstreetjournal.core.data.source.local.room.NewsDao

class LocalDataSource(private val newsDao: NewsDao){
    companion object {
        private var instance :LocalDataSource? = null
    }

    fun getAllNews():Flow<List<NewsEntity>> = newsDao.getAllNews()

    fun getFavoriteNews(): Flow<List<NewsEntity>> = newsDao.getNewsFavorite()

    suspend fun insertNews(newsList :List<NewsEntity> ) = newsDao.insertNews(newsList)

    fun setFavoriteNews(news : NewsEntity, newState : Boolean){

        news.isFavorite = newState
        newsDao.updateFavoriteNew(news)
    }

}