package com.tiooooo.data.news.implementation.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tiooooo.data.news.implementation.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: NewsEntity)

    @Update
    fun updateNews(news: NewsEntity)

    @Delete
    fun deleteNews(news: NewsEntity)

    @Query("SELECT * from NewsEntity")
    fun getNews(): List<NewsEntity>
}