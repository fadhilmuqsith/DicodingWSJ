package org.d3if4048.thewallstreetjournal.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import org.d3if4048.thewallstreetjournal.core.data.source.local.LocalDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity
import org.d3if4048.thewallstreetjournal.core.data.source.remote.RemoteDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiResponse
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse
import org.d3if4048.thewallstreetjournal.core.domain.model.News
import org.d3if4048.thewallstreetjournal.core.domain.repository.INewsRepository
import org.d3if4048.thewallstreetjournal.core.utils.AppExecutors
import org.d3if4048.thewallstreetjournal.core.utils.DataMapper

class NewsRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors) : INewsRepository{
        companion object {
        @Volatile
        private var instance: NewsRepository? = null

            fun getInstance(
                remoteData: RemoteDataSource,
                localData: LocalDataSource,
                appExecutors: AppExecutors
            ) : NewsRepository =
                instance?: synchronized(this){
                    instance?: NewsRepository(remoteData,localData,appExecutors)
                }
        }

    override fun getAllNews() : LiveData<Resource<List<News>>> =
        object : NetworkBoundResource<List<News> , List<NewsResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<News>> {
                return Transformations.map(localDataSource.getAllNews()){
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<News>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<NewsResponse>>> =
                remoteDataSource.getAllNews()


            override fun saveCallResult(data: List<NewsResponse>) {
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asLiveData()

    override fun getFavoriteNews() : LiveData<List<News>> {
        return Transformations.map(localDataSource.getFavoriteNews()){
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteNews(news:News, state:Boolean) {
        val newsEntity = DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute{localDataSource.setFavoriteNews(newsEntity,state)}
    }
}