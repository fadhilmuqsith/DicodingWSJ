package org.d3if4048.thewallstreetjournal.core.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.d3if4048.thewallstreetjournal.core.data.NewsRepository
import org.d3if4048.thewallstreetjournal.core.data.source.local.LocalDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.local.room.NewsDatabase
import org.d3if4048.thewallstreetjournal.core.data.source.remote.RemoteDataSource
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiService
import org.d3if4048.thewallstreetjournal.core.domain.repository.INewsRepository
import org.d3if4048.thewallstreetjournal.core.utils.AppExecutors
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module { factory { get<NewsDatabase>().newsDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,"news.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120,TimeUnit.SECONDS)
            .readTimeout(120,TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single {
        LocalDataSource(
            get()
        )
    }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<INewsRepository> {
        NewsRepository(
            get(),
            get(),
            get()
        )
    }
}