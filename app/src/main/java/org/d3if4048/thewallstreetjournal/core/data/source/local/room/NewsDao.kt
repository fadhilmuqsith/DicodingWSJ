package org.d3if4048.thewallstreetjournal.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAllNews() : LiveData<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE isFavorite = 1")
    fun getNewsFavorite(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews (news: List<NewsEntity>)

    @Update
    fun updateFavoriteNew(news : NewsEntity)
}