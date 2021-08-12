package com.example.desafioframework.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

object Utils {

    fun isOnline(context: Context): Boolean {
        return if (context != null) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var networkInfo: NetworkInfo? = null
            if (connectivityManager != null) {
                networkInfo = connectivityManager.activeNetworkInfo
            }
            networkInfo != null && networkInfo.isConnected
        } else {
            false
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetworkInfo = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetworkInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetworkInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetworkInfo.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }
}