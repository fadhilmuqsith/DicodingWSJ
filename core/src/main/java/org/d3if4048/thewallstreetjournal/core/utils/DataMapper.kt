package org.d3if4048.thewallstreetjournal.core.utils

import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity
import org.d3if4048.thewallstreetjournal.core.data.source.remote.response.NewsResponse
import org.d3if4048.thewallstreetjournal.core.domain.model.News

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

    fun mapEntitiesToDomain(input : List<NewsEntity>) : List<News> =
        input.map {
            News(
                newsId = it.newsId,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input : News) = NewsEntity(
        newsId = input.newsId,
        author = input.author,
        title = input.title,
        description = input.description,
        url = input.url,
        urlToImage = input.urlToImage,
        publishedAt = input.publishedAt,
        content = input.content,
        isFavorite = input.isFavorite
    )
}