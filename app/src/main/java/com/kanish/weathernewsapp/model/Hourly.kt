package com.kanish.weathernewsapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
data class Hourly(
    @SerializedName("data")  val data: List<HourlyData>,
    val icon: String,
    val summary: String
)