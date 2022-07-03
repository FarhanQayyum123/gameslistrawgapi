package com.example.kamal.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {
    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
