package org.d3if4048.thewallstreetjournal.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val newsId : Int,
    val author: String,
    val title: String,
    val description: String,
    val url:String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val isFavorite : Boolean
):Parcelable