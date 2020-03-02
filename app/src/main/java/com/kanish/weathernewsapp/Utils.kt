package com.kanish.weathernewsapp
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Kanish Roshan on 2020-03-01.
 */
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}