package com.example.kamal.myapplication.model

class GamesListModel {
    val id = 0
    val background_image: String? = ""
        get() = field ?: "http"
    val name: String? = ""
        get() = field ?: ""
    val released: String? = ""
        get() = field ?: ""
    val rating = 0.0
    val reviews_count = 0
}