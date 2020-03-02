package com.kanish.weathernewsapp.model

import com.google.gson.annotations.SerializedName
/**
 * Created by Kanish Roshan on 2020-03-01.
 */
data class WeatherResponse(
    @SerializedName("body") val weatherResponseBody: WeatherResponseBody,
    val message: String,
    val status: Boolean
)