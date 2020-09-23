package org.d3if4048.thewallstreetjournal.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.d3if4048.thewallstreetjournal.core.data.source.local.LocalDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity
import org.d3if4048.thewallstreetjournal.core.data.source.remote.RemoteDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiResponse
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse
import org.d3if4048.thewallstreetjournal.core.domain.model.News
import org.d3if4048.thewallstreetjournal.core.domain.repository.INewsRepository
import org.d3if4048.thewallstreetjournal.core.utils.AppExecutors
import org.d3if4048.thewallstreetjournal.core.utils.DataMapper

class NewsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors) : INewsRepository{
        companion object {
        @Volatile
        private var instance: NewsRepository? = null
        }

    override fun getAllNews() : Flow<Resource<List<News>>> =
        object : NetworkBoundResource<List<News> , List<NewsResponse>>() {
            override fun loadFromDB(): Flow<List<News>> {
                return  localDataSource.getAllNews().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<News>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<NewsResponse>>> =
                remoteDataSource.getAllNews()


            override suspend fun saveCallResult(data: List<NewsResponse>) {
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asFlow()

    override fun getFavoriteNews() : Flow<List<News>> {
        return  localDataSource.getFavoriteNews().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteNews(news:News, state:Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute{localDataSource.setFavoriteNews(newsEntity,state)}
    }
}