package org.d3if4048.thewallstreetjournal.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.d3if4048.thewallstreetjournal.core.data.source.local.entity.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAllNews() : Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE isFavorite = 1")
    fun getNewsFavorite(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews (news: List<NewsEntity>)

    @Update
    fun updateFavoriteNew(news : NewsEntity)
}