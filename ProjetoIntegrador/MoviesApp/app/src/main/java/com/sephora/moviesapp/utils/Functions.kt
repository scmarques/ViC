package com.sephora.moviesapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Functions {

    fun Double.getVoteAverageString(num: Double): String? {
        val votePercent = num * 10
        return "%.0f".format(votePercent) + "%"
    }

    fun checkNetworkStatus(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val capabilities = manager?.getNetworkCapabilities(manager.activeNetwork) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}

