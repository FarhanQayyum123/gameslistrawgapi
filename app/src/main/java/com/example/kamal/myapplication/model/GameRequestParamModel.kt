package com.example.kamal.myapplication.model

class GameRequestParamModel {
    var page = 0
    var itemId: String? = ""
        get() = field ?: ""
        set(value) { field = value }
}