package com.tiooooo.data.news.implementation.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tiooooo.data.news.implementation.local.dao.NewsDao
import com.tiooooo.data.news.implementation.local.entity.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
    ],
    version = 1,
)
abstract class NewsDb : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    companion object {
        private const val DB_NAME = "News.db"

        @Volatile
        private var INSTANCE: NewsDb? = null

        @Synchronized
        fun getInstance(ctx: android.content.Context): NewsDb {
            if (INSTANCE == null) {
                INSTANCE = androidx.room.Room.databaseBuilder(
                    ctx.applicationContext,
                    NewsDb::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as NewsDb
        }
    }
}