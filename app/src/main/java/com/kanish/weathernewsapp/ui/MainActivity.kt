package com.kanish.weathernewsapp.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.kanish.weathernewsapp.Injection
import com.kanish.weathernewsapp.R
import com.kanish.weathernewsapp.databinding.ActivityMainBinding
import com.kanish.weathernewsapp.isNetworkAvailable
import com.kanish.weathernewsapp.ui.adapters.AdapterCallBackListener
import com.kanish.weathernewsapp.ui.adapters.NewsListAdapter
import kotlinx.android.synthetic.main.weather_layout.*
import kotlinx.coroutines.InternalCoroutinesApi

import java.lang.ref.WeakReference

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
class MainActivity : AppCompatActivity(), AdapterCallBackListener {
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private val PERMISSION_ID: Int = 100
    private lateinit var viewModel: MainViewModel
    private lateinit var newsAdapter: NewsListAdapter
    private lateinit var newsList: RecyclerView
    private lateinit var activityMainBinding: ActivityMainBinding


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )


        newsList = findViewById(R.id.news_list)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this,lat, lon)).get(MainViewModel::class.java)
        activityMainBinding.viewmodel = viewModel
        getGPS()
        initRecyclerView()
        registerObserver()
        getNewsResponse()


    }

    private fun registerObserver() {
        viewModel.connectionErronetWorkErrorWeatherr.observe(this , Observer {
            tv_weather_title.text=it
        })

        viewModel.connectionError.observe(this , Observer {
            showDialog(it)
        })
        viewModel.netWorkError.observe(this , Observer {
            showDialog(it)
        })
        viewModel.genericError.observe(this , Observer {
            showDialog(it)
        })

        viewModel.mutableNewsResponse.observe(this, Observer {
            viewModel.progressSate.set(false)
            newsAdapter.setArticlesResponse(it)
        })

    }

    private fun setWeatherData() {
        viewModel.mutablWeatherResponse.observe(this, Observer { weatherResponse ->
            Log.d("kanish", "weather data " + weatherResponse?.weatherResponseBody?.hourly)
            weatherResponse?.weatherResponseBody?.let {
                tv_weather_summary.text= it.hourly.summary
               val maxTemp= viewModel.maxTemp?.temperature
                val minTemp= viewModel.minTemp?.temperature
                tv_weather_max_min.text = maxTemp.toString() +" "+"\u2103" +"/"+ minTemp.toString() +" "+"\u2103"
                tv_weather_title.text= it?.currently?.temperature.toString() +" "+"\u2103"
                var humidity=it?.currently?.humidity *100
                var   humidityValue=humidity.toString()
                humidityValue=humidityValue.substring(0,humidityValue.indexOf('.'))
                tv_weather_per.text= "$humidityValue % today"
            }
        })
    }


    private fun getGPS() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient?.lastLocation?.let { task ->
                    task.addOnCompleteListener {
                        val location: Location? = it.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            lat =location.latitude
                            lon = location.longitude
                            viewModel.getWeatherData(lat,lon)
                            setWeatherData()

                        }
                    }
                }
            }
        } else {
            requestPermissions()
        }
    }


    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            lat = mLastLocation.latitude
            lon = mLastLocation.longitude
            viewModel.getWeatherData(lat,lon)
            setWeatherData()

        }
    }

    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient?.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }


    private fun initRecyclerView() {
        newsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            newsAdapter = NewsListAdapter(WeakReference(this@MainActivity),this@MainActivity)
            adapter = newsAdapter
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               getGPS()
            }
        }
    }

    fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    @InternalCoroutinesApi
    private fun getNewsResponse() {
        viewModel.getNewsData()
//        if (isNetworkAvailable(this)) {
//            viewModel.getNewsData()
//        } else {
//            viewModel.progressSate.set(false)
//        }

    }

    override fun onItemClickListener(url: String?) {
        val intent=Intent(this,NewsBrowserActivity::class.java)
        intent.putExtra("uri",url)
        startActivity(intent)
    }

  private fun showDialog(dialogMessage:String){
       val dialogBuilder=AlertDialog.Builder(this)
       dialogBuilder.apply {
           this.setMessage(dialogMessage)
           this.setTitle("Weather News APP")
         this.setCancelable(false)
           this.setPositiveButton("OK") { dialog, id ->
               finish()
           }
       }.show()

   }

}
