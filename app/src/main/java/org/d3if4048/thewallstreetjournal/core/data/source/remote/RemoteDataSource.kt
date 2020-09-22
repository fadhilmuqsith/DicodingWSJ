package org.d3if4048.thewallstreetjournal.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiResponse
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiService
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.ListNewsResponse
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource private constructor(private val apiService: ApiService){
    companion object{
        @Volatile
        private var instance :RemoteDataSource? = null

        fun getInstanc(service: ApiService) :RemoteDataSource =
            instance?: synchronized(this){
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllNews(): LiveData<ApiResponse<List<NewsResponse>>>{
            val resultData= MutableLiveData<ApiResponse<List<NewsResponse>>>()

           val client = apiService.getList()
            client.enqueue(object : Callback<ListNewsResponse>{
                override fun onResponse(
                    call: Call<ListNewsResponse>,
                    response: Response<ListNewsResponse>
                ) {
                    val dataArray = response.body()?.articles
                    resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
                }

                override fun onFailure(call: Call<ListNewsResponse>, t: Throwable) {
                    resultData.value = ApiResponse.Error(t.message.toString())
                    Log.e("RemoteDataSource",t.message.toString())
                }
            })
        return resultData
    }
}