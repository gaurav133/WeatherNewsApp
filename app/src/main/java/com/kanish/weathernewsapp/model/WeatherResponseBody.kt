package com.kanish.weathernewsapp.model

import com.google.gson.annotations.SerializedName
/**
 * Created by Kanish Roshan on 2020-03-01.
 */
data class WeatherResponseBody(
    @SerializedName("abbr") val abbr: String,
    @SerializedName("currently")  val currently: Currently,
    @SerializedName("hourly") val hourly: Hourly,
    @SerializedName("latitude")  val latitude: Double,
    @SerializedName("longitude")   val longitude: Double,
    @SerializedName("offset")  val offset: Double,
    @SerializedName("timezone")   val timezone: String
)