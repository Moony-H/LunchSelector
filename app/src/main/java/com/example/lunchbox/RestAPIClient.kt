package com.example.lunchbox

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RestAPIClient{
    @GET("/v2/local/search/keyword.json")
    fun getFromKeyword(
        @Header("Authorization") key:String,
        @Query("query") Keyword:String,
        @Query("x") x:String,
        @Query("x") y:String,
        @Query("sort") sort:String
    ): Call<KeywordSearchData>
}



