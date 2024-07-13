package com.tiooooo.data.news.implementation.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.tiooooo.core.constant.Constants
import com.tiooooo.core.network.data.States
import com.tiooooo.core.network.data.toError
import com.tiooooo.core.util.wrapEspressoIdlingResource
import com.tiooooo.data.news.api.model.News
import com.tiooooo.data.news.api.repository.NewsRepository
import com.tiooooo.data.news.implementation.local.dao.NewsDao
import com.tiooooo.data.news.implementation.local.entity.toDomain
import com.tiooooo.data.news.implementation.remote.response.toNewsEntity
import com.tiooooo.data.news.implementation.remote.service.NewsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao,
    private val userDataStorePreferences: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher,
) : NewsRepository {
    override suspend fun getNews(
        isSortByLatest: Boolean,
        isSortByRank: Boolean,
        isRefresh: Boolean
    ): Flow<States<List<News>>> = flow {
        wrapEspressoIdlingResource {
            try {
                emit(States.Loading)
                val lastHitTime = getLastHitTime().first()
                if (validateTime(lastHitTime, isRefresh)) {

                    val response = newsService.getNews()
                    setLastHitTime(System.currentTimeMillis())
                    response.map { newsResponse ->
                        newsDao.insertNews(newsResponse.toNewsEntity())
                    }
                }

                val news = createQuery(
                    news = newsDao.getNews().map { it.toDomain() },
                    isSortByLatest = isSortByLatest,
                    isSortByRank = isSortByRank,
                )

                if (news.isEmpty()) {
                    emitAll(getNews(isSortByLatest, isSortByRank, true))
                } else {
                    emit(States.Success(news))
                }
            } catch (e: Exception) {
                val news = createQuery(
                    news = newsDao.getNews().map { it.toDomain() },
                    isSortByLatest = isSortByLatest,
                    isSortByRank = isSortByRank,
                )
                if (news.isNotEmpty()) {
                    emit(States.Success(news))
                } else {
                    emit(e.toError())
                }
            }
        }
    }.flowOn(ioDispatcher)


    private fun createQuery(
        news: List<News>,
        isSortByLatest: Boolean,
        isSortByRank: Boolean,
    ): List<News> {
        return if (isSortByLatest) {
            news.sortedByDescending { it.timeCreated }
        } else if (isSortByRank) news.sortedByDescending { it.rank }
        else news
    }

    private fun getLastHitTime(): Flow<Long> {
        return userDataStorePreferences.data.map {
            it[longPreferencesKey(Constants.DS_LAST_TIME_GET_DATA)] ?: System.currentTimeMillis()
        }
    }

    private suspend fun setLastHitTime(time: Long) {
        userDataStorePreferences.edit {
            it[longPreferencesKey(Constants.DS_LAST_TIME_GET_DATA)] = time
        }
    }

    private fun validateTime(lastHitTime: Long, isRefresh: Boolean): Boolean {
        return if (isRefresh) {
            true
        } else {
            System.currentTimeMillis() - lastHitTime > 60000
        }
    }
}