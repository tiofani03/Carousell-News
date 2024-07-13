package com.tiooooo.data.news.implementation.remote.service

import com.tiooooo.data.news.implementation.remote.response.NewsResponse
import retrofit2.http.GET

interface NewsService {

    @GET("carousell_news.json")
    suspend fun getNews(): List<NewsResponse>
}