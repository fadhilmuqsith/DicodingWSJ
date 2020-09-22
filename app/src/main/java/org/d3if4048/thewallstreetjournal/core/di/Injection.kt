package org.d3if4048.thewallstreetjournal.core.di

import android.content.Context
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository
import org.d3if4048.thewallstreetjournal.core.data.source.local.LocalDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.local.room.NewsDatabase
import org.d3if4048.thewallstreetjournal.core.data.source.remote.RemoteDataSource
import org.d3if4048.thewallstreetjournal.core.utils.AppExecutors
import org.d3if4048.thewallstreetjournal.core.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context):NewsRepository{
        val database= NewsDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstanc(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.newsDao())
        val appExecutors = AppExecutors()

        return NewsRepository.getInstance(remoteDataSource,localDataSource,appExecutors)
    }
}