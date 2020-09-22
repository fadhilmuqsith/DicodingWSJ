package org.d3if4048.thewallstreetjournal.core.utils

import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse

object DataMapper {
    fun mapResponsesToEntities(input : List<NewsResponse>) :List<NewsEntity> {
        val newList= ArrayList<NewsEntity>()
        input.map {

            val news = NewsEntity(
                newsId = 0,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url.toString(),
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isFavorite = false
            )
            newList.add(news)
        }
        return newList
    }
}