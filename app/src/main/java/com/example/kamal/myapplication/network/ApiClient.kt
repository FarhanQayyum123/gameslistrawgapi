package com.example.kamal.myapplication.network

import com.example.kamal.myapplication.utils.ConfigUtils
import com.example.kamal.myapplication.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit? = null
    @JvmStatic
    val clientAuthentication: Retrofit?
        get() {
            val httpClient = OkHttpClient.Builder()
            val builder = Retrofit.Builder()
                    .baseUrl(ConfigUtils.BASE_URL)
            httpClient.readTimeout(Constants.READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
            httpClient.connectTimeout(Constants.CONNECTION_TIME_OUT.toLong(), TimeUnit.SECONDS)
            builder.client(httpClient.build())
            retrofit = builder
                    .baseUrl(ConfigUtils.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build()
            return retrofit
        }
}