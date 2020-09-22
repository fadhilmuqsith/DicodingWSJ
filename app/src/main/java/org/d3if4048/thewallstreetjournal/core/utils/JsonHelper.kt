package org.d3if4048.thewallstreetjournal.core.utils

import android.content.Context
import org.d3if4048.thewallstreetjournal.R
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse
import org.json.JSONObject
import java.io.IOException

class JsonHelper (private val context: Context) {
    private fun parsingFileToString():String?{
        val jsonString : String
        try {
            jsonString = context.resources.openRawResource(R.raw.wallstreet).bufferedReader().use { it.readText() }
        }
        catch (ioException : IOException){
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun loadData():List<NewsResponse> {
        val list = ArrayList<NewsResponse>()
        val responseObject =JSONObject(parsingFileToString().toString())
        val listArray = responseObject.getJSONArray("articles")
        for (i in 0 until listArray.length()) {
            val course= listArray.getJSONObject(i)

            val author = course.getString("author")
            val title = course.getString("title")
            val description = course.getString("description")
            val url = course.getString("url")
            val urlToImage = course.getString("urlToImage")
            val publishedAt = course.getString("publishedAt")
            val content = course.getString("content")

            val courseResponse = NewsResponse (
                author = author,
                title = title,
                description = description,
                url = url.toString(),
                urlToImage = urlToImage,
                publishedAt = publishedAt,
                content = content
            )
            list.add(courseResponse)
        }
        return list

    }
}