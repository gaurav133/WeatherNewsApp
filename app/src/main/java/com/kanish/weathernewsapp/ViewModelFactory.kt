package com.kanish.weathernewsapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kanish.weathernewsapp.data.Repository
import com.kanish.weathernewsapp.ui.MainViewModel

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
class ViewModelFactory (private val repository: Repository,private  val application: Context,val lat:Double,val lon:Double):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository,application.applicationContext,lat,lon) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}