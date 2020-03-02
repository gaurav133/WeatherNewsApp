package com.kanish.weathernewsapp.model

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
data class Flags(
    val nearest_station: Double,
    val sources: List<String>,
    val units: String
)