package com.example.kamal.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class APIResponseHttp<T> {
    @SerializedName("count")
    @Expose
    val count = 0

    @SerializedName("next")
    @Expose
    val next: String? = null

    @SerializedName("previous")
    @Expose
    val previous: String? = null
    
    val results: T? = null
}