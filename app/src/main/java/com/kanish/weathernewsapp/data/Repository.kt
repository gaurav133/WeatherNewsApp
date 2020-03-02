package com.kanish.weathernewsapp.data

import android.content.Context
import com.kanish.weathernewsapp.data.database.NewsDataBase
import com.kanish.weathernewsapp.data.network.RetroClient
import kotlinx.coroutines.flow.flow


class Repository(
    val applicationContext: Context
) {


    suspend fun getWeatherData( lat:String, long:String)=RetroClient.provideApi().getWeather(lat,long)

    //Acts as Single Source of truth for MainViewModel
    suspend fun getNewsFeed() =flow {

        //get the data from local
       val localData= NewsDataBase.getDbInstance(applicationContext).getApiCacheDao()!!.getCacheNewsList()
        emit(localData)

        //get the data from remote
        val dataRemote =RetroClient.provideApi().getNewsFeed()
        if (!dataRemote.newsFeedBody.newsItem.isNullOrEmpty()){
            //
            NewsDataBase.getDbInstance(applicationContext).getApiCacheDao()!!.deleteAll()
            //update local DB
            NewsDataBase.getDbInstance(applicationContext).getApiCacheDao()!!.insertAll(dataRemote.newsFeedBody.newsItem)
            //get fresh data
            val updatedLocalData= NewsDataBase.getDbInstance(applicationContext).getApiCacheDao()!!.getCacheNewsList()
            emit(updatedLocalData)

        }


    }

}