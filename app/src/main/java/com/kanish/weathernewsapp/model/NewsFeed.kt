package com.kanish.weathernewsapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
data class NewsFeed(
    @SerializedName("body") val newsFeedBody: NewsFeedBody,
    val message: String,
    val status: Boolean
)