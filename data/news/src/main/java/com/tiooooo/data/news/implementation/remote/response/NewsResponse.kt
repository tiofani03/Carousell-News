package com.tiooooo.data.news.implementation.remote.response

import com.google.gson.annotations.SerializedName
import com.tiooooo.data.news.implementation.local.entity.NewsEntity

data class NewsResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("banner_url") val bannerUrl: String?,
    @SerializedName("time_created") val timeCreated: Long?,
    @SerializedName("rank") val rank: Int?,
)

fun NewsResponse?.toNewsEntity() = NewsEntity(
    id = this?.id.orEmpty(),
    title = this?.title.orEmpty(),
    description = this?.description.orEmpty(),
    bannerUrl = this?.bannerUrl.orEmpty(),
    timeCreated = this?.timeCreated ?: System.currentTimeMillis(),
    rank = this?.rank ?: 0
)
