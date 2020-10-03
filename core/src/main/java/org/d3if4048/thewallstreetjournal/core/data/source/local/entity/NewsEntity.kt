package org.d3if4048.thewallstreetjournal.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "newsId")
    var newsId :Int,

    @ColumnInfo(name = "author")
    var author :String?,

    @ColumnInfo(name = "title")
    var title :String,

    @ColumnInfo(name = "description")
    var description :String,

    @ColumnInfo(name = "url")
    var url :String,

    @ColumnInfo(name = "urlToImage")
    var urlToImage :String,

    @ColumnInfo(name = "publishedAt")
    var publishedAt :String,

    @ColumnInfo(name = "content")
    var content :String?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite :Boolean = false
) :Parcelable