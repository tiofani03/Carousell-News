package com.tiooooo.data.news.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tiooooo.core.common.ContextProvider
import com.tiooooo.core.di.IoDispatcher
import com.tiooooo.data.news.api.repository.NewsRepository
import com.tiooooo.data.news.implementation.local.NewsDb
import com.tiooooo.data.news.implementation.local.dao.NewsDao
import com.tiooooo.data.news.implementation.remote.service.NewsService
import com.tiooooo.data.news.implementation.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsService: NewsService,
        newsDao: NewsDao,
        userDataStorePreferences: DataStore<Preferences>,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): NewsRepository {
        return NewsRepositoryImpl(
            newsService = newsService,
            newsDao = newsDao,
            userDataStorePreferences = userDataStorePreferences,
            ioDispatcher = ioDispatcher,
        )
    }

    @Singleton
    @Provides
    fun provideNewsDb(contextProvider: ContextProvider): NewsDb {
        return NewsDb.getInstance(contextProvider.getContext())
    }

    @Singleton
    @Provides
    fun provideNewsDao(newsDb: NewsDb) = newsDb.newsDao()


}