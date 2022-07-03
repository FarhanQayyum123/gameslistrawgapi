package com.example.kamal.myapplication.model

class GameItemDetailModel {
    val id = 0
    val background_image: String? = ""
        get() = field ?: "http"
    val name: String? = ""
        get() = field ?: ""
    val updated: String? = ""
        get() = field ?: ""
    val description: String? = ""
        get() = field ?: ""

}