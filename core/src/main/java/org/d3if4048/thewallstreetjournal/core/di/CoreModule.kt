package org.d3if4048.thewallstreetjournal.core.di

import androidx.room.Room
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("news".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,"news.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}

val networkModule = module {
    single {
        val hostname = "newsapi.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname,"sha256/LAlZB272xQABCgeTFXzq0MuyQTFpu4lb7LOBjVoJdrE=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120,TimeUnit.SECONDS)
            .readTimeout(120,TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://newsapi.org/")
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
