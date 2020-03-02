package com.kanish.weathernewsapp.model

import com.google.gson.annotations.SerializedName
/**
 * Created by Kanish Roshan on 2020-03-01.
 */
data class NewsFeedBody(
    @SerializedName("resultSet")val newsItem: List<NewsItem>,
    val size: Int,
    val status: String
)