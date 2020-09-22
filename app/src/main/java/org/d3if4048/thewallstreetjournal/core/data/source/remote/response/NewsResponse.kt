package org.d3if4048.thewallstreetjournal.core.data.source.remote.response

data class NewsResponse (
    val author: String,
    val title: String,
    val description: String,
    val url:String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)