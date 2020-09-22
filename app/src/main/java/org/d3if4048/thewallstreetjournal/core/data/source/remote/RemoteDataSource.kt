package org.d3if4048.thewallstreetjournal.core.data.source.remote

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.d3if4048.thewallstreetjournal.core.data.source.remote.network.ApiResponse
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse
import org.d3if4048.thewallstreetjournal.core.utils.JsonHelper
import org.json.JSONException


class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){
    companion object{
        @Volatile
        private var instance :RemoteDataSource? = null

        fun getInstanc(helper: JsonHelper) :RemoteDataSource =
            instance?: synchronized(this){
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllNews(): LiveData<ApiResponse<List<NewsResponse>>>{
            val resultData= MutableLiveData<ApiResponse<List<NewsResponse>>>()

        val handler = Handler()

        handler.postDelayed({
            try {
                val dataArray = jsonHelper.loadData()
                if (dataArray.isNotEmpty()){
                    resultData.value = ApiResponse.Success(dataArray)
                }
                else {
                    resultData.value = ApiResponse.Empty
                }
            }
            catch (e : JSONException){
                resultData.value = ApiResponse.Error(e.toString())
                Log.e("RemoteDataSource",e.toString())
            }
        },2000)

        return resultData
    }
}