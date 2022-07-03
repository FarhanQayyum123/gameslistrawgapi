package com.example.kamal.myapplication.network

import com.example.kamal.myapplication.model.GameItemDetailModel
import com.example.kamal.myapplication.model.GamesListModel
import com.example.kamal.myapplication.model.WebResponseHttp
import com.example.kamal.myapplication.utils.ConfigUtils
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface APIInterface {
    // RxJava Call
    @GET("games?key=" + ConfigUtils.API_KEY_TOKEN + "&ordering=released")
    fun getGames(@Query("page_size") page_size: String?, @Query("page") page: String?): Single<WebResponseHttp<ArrayList<GamesListModel?>?>?>?

    @GET("games/{ITEM_ID}?key=" + ConfigUtils.API_KEY_TOKEN)
    fun getItemDetail(@Path("ITEM_ID") ITEM_ID: String?): Single<GameItemDetailModel?>
}