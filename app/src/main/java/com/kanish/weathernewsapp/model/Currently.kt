package com.kanish.weathernewsapp.model

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
data class Currently(
    val apparentTemperature: Double,
    val cloudCover: Double,
    val dewPoint: Double,
    val humidity: Double,
    val icon: String,
    val ozone: Double,
    val precipIntensity: Double,
    val precipProbability: Double,
    val pressure: Double,
    val summary: String,
    val temperature: Double,
    val time: Long,
    val uvIndex: Double,
    val visibility: Double,
    val windBearing: Double,
    val windGust: Double,
    val windSpeed: Double
)