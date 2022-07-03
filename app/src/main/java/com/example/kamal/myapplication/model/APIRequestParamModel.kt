package com.example.kamal.myapplication.model

class APIRequestParamModel {
    var page = 0
    var itemId: String? = ""
        get() = field ?: ""
}