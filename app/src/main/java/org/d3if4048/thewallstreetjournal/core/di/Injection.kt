package org.d3if4048.thewallstreetjournal.core.di

import android.content.Context
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository
import org.d3if4048.thewallstreetjournal.core.data.source.local.LocalDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.local.room.NewsDatabase
import org.d3if4048.thewallstreetjournal.core.data.source.remote.RemoteDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiConfig
import org.d3if4048.thewallstreetjournal.core.domain.repository.INewsRepository
import org.d3if4048.thewallstreetjournal.core.domain.usecase.NewsInteractor
import org.d3if4048.thewallstreetjournal.core.domain.usecase.NewsUseCase
import org.d3if4048.thewallstreetjournal.core.utils.AppExecutors
import org.d3if4048.thewallstreetjournal.core.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context):INewsRepository{
        val database= NewsDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstanc(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.newsDao())
        val appExecutors = AppExecutors()

        return NewsRepository.getInstance(remoteDataSource,localDataSource,appExecutors)
    }

    fun provideNewsUseCase(context: Context) : NewsUseCase {
        val repository = provideRepository(context)
        return NewsInteractor(repository)
    }
}