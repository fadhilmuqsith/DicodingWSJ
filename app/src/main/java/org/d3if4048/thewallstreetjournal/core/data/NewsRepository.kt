package org.d3if4048.thewallstreetjournal.core.data

import androidx.lifecycle.LiveData
import org.d3if4048.thewallstreetjournal.core.data.source.local.LocalDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity
import org.d3if4048.thewallstreetjournal.core.data.source.remote.RemoteDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiResponse
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse
import org.d3if4048.thewallstreetjournal.core.utils.AppExecutors
import org.d3if4048.thewallstreetjournal.core.utils.DataMapper

class NewsRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors){
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

    fun getAllNews() : LiveData<Resource<List<NewsEntity>>> =
        object : NetworkBoundResource<List<NewsEntity> , List<NewsResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<NewsEntity>> {
                return localDataSource.getAllNews()
            }

            override fun shouldFetch(data: List<NewsEntity>?): Boolean = data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<NewsResponse>>> =
                remoteDataSource.getAllNews()


            override fun saveCallResult(data: List<NewsResponse>) {
                val newsList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asLiveData()

    fun getFavoriteNews() : LiveData<List<NewsEntity>> {
        return localDataSource.getFavoriteNews()
    }

    fun setFavoriteNews(news:NewsEntity,state:Boolean) {
        appExecutors.diskIO().execute{localDataSource.setFavoriteNews(news,state)}
    }
}