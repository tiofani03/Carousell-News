package com.tiooooo.newstest.helper

import com.tiooooo.data.news.api.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun generateListNews(): List<News> {
    val list = mutableListOf<News>()

    for (i in 0..10) {
        list.add(
            News(
                id = randomString(),
                title = randomString(),
                description = randomString(),
                bannerUrl = randomString(),
                timeCreated = randomLong(),
                rank = randomInt()
            )
        )
    }
    return list
}
