package com.kanish.weathernewsapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.kanish.weathernewsapp.data.Repository
import com.kanish.weathernewsapp.data.database.NewsDataBase
import java.util.concurrent.Executors


/**
 * Created by Kanish Roshan on 2020-03-01.
 */

object Injection {


    private fun provideRepository(context: Context): Repository {
        return Repository(context.applicationContext)
    }


    fun provideViewModelFactory(context: Context,lat:Double,lon:Double): ViewModelProvider.Factory {
        return ViewModelFactory(provideRepository(context), context.applicationContext,lat,lon)
    }
}