package com.tiooooo.data.news.implementation.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tiooooo.data.news.api.model.News

@Entity
data class NewsEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val bannerUrl: String,
    val timeCreated: Long,
    val rank: Int,
)


fun NewsEntity.toDomain() = News(
    id = id,
    title = title,
    description = description,
    bannerUrl = bannerUrl,
    timeCreated = timeCreated * 1000,
    rank = rank,
)