package org.d3if4048.thewallstreetjournal.core.data.source.remote.network

import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.ListNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Response

interface ApiService {
    @GET("v2/everything?domains=wsj.com&apiKey=01d7123930674cc9a179e35f2b770ff3")
    suspend fun getList(): ListNewsResponse

}