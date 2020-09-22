package org.d3if4048.thewallstreetjournal.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiResponse
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiService
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.ListNewsResponse
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class RemoteDataSource private constructor(private val apiService: ApiService){
    companion object{
        @Volatile
        private var instance :RemoteDataSource? = null

        fun getInstanc(service: ApiService) :RemoteDataSource =
            instance?: synchronized(this){
                instance ?: RemoteDataSource(service)
            }
    }

   suspend fun getAllNews() : Flow<ApiResponse<List<NewsResponse>>> {
       return flow {
           try {
               val response = apiService.getList()
               val dataArray = response.articles
               if (dataArray.isNotEmpty()){
                   emit(ApiResponse.Success(response.articles))
               }
               else {
                   emit(ApiResponse.Empty)
               }
           }
           catch (e : Exception){
               emit(ApiResponse.Error(e.toString()))
               Log.e("RemoteDataSource",e.toString())
           }
       }.flowOn(Dispatchers.IO)
   }
}