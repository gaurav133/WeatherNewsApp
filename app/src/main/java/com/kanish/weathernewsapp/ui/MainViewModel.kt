package com.kanish.weathernewsapp.ui

import android.content.Context

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.kanish.weathernewsapp.data.Repository
import com.kanish.weathernewsapp.model.HourlyData
import com.kanish.weathernewsapp.model.NewsItem
import com.kanish.weathernewsapp.model.WeatherResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
class MainViewModel(
    repository: Repository,
    context: Context,
    var lat: Double,
    var lon: Double
) : ViewModel() {


    var maxTemp: HourlyData? = null
    var minTemp: HourlyData? = null
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(IO + viewModelJob)
    private val repository = Repository(context)

    val mutableNewsResponse = MutableLiveData<List<NewsItem>>()
    val progressSate = ObservableField(true)
    val genericError=MutableLiveData<String>()
    val connectionError=MutableLiveData<String>()
    val connectionErronetWorkErrorWeatherr=MutableLiveData<String>()
    val netWorkError=MutableLiveData<String>()

    val mutablWeatherResponse = liveData(IO) {
        val newsFeedResponse: WeatherResponse?
        try {
            if (lat == 0.0 || lon == 0.0) {
                //IF location data could not retrieve
                newsFeedResponse = repository.getWeatherData("17", "73")
            } else {
                newsFeedResponse = repository.getWeatherData(lat.toString(), lon.toString())
                maxTemp = newsFeedResponse.weatherResponseBody.hourly.data.maxBy { it.temperature }
                minTemp = newsFeedResponse.weatherResponseBody.hourly.data.minBy { it.temperature }
            }

            emit(newsFeedResponse)
        } catch (throwable: Exception) {
            // Error Handling can be moved on Repository Layer(if We have Error Contract like 404 ,501 ) :
            when(throwable ){
                is IOException -> showWeatherError()
                is HttpException -> showWeatherError()
                else -> showWeatherError()
            }
        }

    }

    private fun showWeatherError() {
        connectionErronetWorkErrorWeatherr.postValue("Could not load Weather Data.Some Error occurred")
    }

    private fun showGenericError() {
        genericError.postValue("Something Went Wrong")
    }

    private fun showNetworkError() {
        netWorkError.postValue("Sorry Could not Connect to Server, Please try later ")
    }

    private fun showConnectionError() {

      //  connectionError.postValue("Sorry Could not Connect , PLease try later  ")
    }

    fun getWeatherData(lat: Double, lon: Double) {
        this.lat = lat
        this.lon = lon


    }


    @InternalCoroutinesApi
    fun getNewsData() {

        viewModelScope.launch {
            try {
                repository.getNewsFeed().collect {
                    //  mutableNewsResponse.postValue(it)
                    mutableNewsResponse.postValue(it)
                }
        } catch (throwable: Exception) {
                // Error Handling can be moved on Repository Layer(if We have Error Contract like 404 ,501 ) :
                when (throwable) {
                    is IOException -> showConnectionError()
                    is HttpException -> showNetworkError()
                    else -> showGenericError()
                }
            }

        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

