package com.example.lunchbox.manager

import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RestAPIClientInterface{
    @GET("v2/local/search/keyword.json")
    fun getFromKeyword(
        @Header("Authorization") key:String,
        @Query("query") Keyword:String,
        @Query("x") x:Double,
        @Query("y") y:Double,
        @Query("radius") radius:Int,
        @Query("sort") sort:String
    ): Call<SearchingWithKeywordDataclass>

    @GET("v2/local/search/keyword.json")
    fun getFromKeyword(
        @Header("Authorization") key:String,
        @Query("query") Keyword:String,
        @Query("x") x:Double,
        @Query("y") y:Double,
        @Query("sort") sort:String
    ):Call<SearchingWithKeywordDataclass>
}
