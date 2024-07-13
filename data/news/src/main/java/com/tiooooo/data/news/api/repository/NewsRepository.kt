package com.tiooooo.data.news.api.repository

import com.tiooooo.core.network.data.States
import com.tiooooo.data.news.api.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(
        isSortByLatest: Boolean = false,
        isSortByRank: Boolean = false,
        isRefresh: Boolean = false,
    ): Flow<States<List<News>>>
}