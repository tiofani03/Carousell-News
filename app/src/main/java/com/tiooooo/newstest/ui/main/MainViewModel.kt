package com.tiooooo.newstest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiooooo.core.network.data.States
import com.tiooooo.data.news.api.model.News
import com.tiooooo.data.news.api.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    private val _news = MutableSharedFlow<States<List<News>>>()
    val news = _news.asSharedFlow()

    fun getNews(
        isRefresh: Boolean = false, isSortByRank: Boolean = false, isSortByLatest: Boolean = false
    ) {
        viewModelScope.launch {
            _news.emitAll(
                newsRepository.getNews(
                    isRefresh = isRefresh,
                    isSortByRank = isSortByRank,
                    isSortByLatest = isSortByLatest
                )
            )
        }
    }
}