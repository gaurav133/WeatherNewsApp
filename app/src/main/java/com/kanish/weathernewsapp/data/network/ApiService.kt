package com.kanish.weathernewsapp.data.network


import com.kanish.weathernewsapp.model.NewsFeed
import com.kanish.weathernewsapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
interface ApiService {

    @GET("weather")
    suspend fun getWeather(@Query("latitude") latitude: String, @Query("longitude") longitude: String): WeatherResponse


    @GET("news")
    suspend fun getNewsFeed(): NewsFeed
}