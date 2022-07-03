package com.example.kamal.myapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

class Utils {

    interface NetWork {
        companion object {
            @SuppressLint("MissingPermission")
            fun isNetworkConnected(context: Context): Boolean {
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
            }
        }
    }



}