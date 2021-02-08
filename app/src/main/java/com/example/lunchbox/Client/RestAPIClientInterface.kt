package com.example.lunchbox.Client

import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RestAPIClientInterface{
    @GET("v2/local/search/keyword.json")

    //주위 반경까지 검색(음식 랜덤 돌릴때 사용)
    fun getFromKeyword(
        @Header("Authorization") key:String,
        @Query("query") Keyword:String,
        @Query("x") x:Double,
        @Query("y") y:Double,
        @Query("radius") radius:Int,
        @Query("sort") sort:String
    ): Call<SearchingWithKeywordDataclass>


    //위치 검색할 때 사용.
    @GET("v2/local/search/keyword.json")
    fun getFromKeyword(
        @Header("Authorization") key:String,
        @Query("query") Keyword:String,
        @Query("x") x:Double,
        @Query("y") y:Double,
        @Query("sort") sort:String
    ):Call<SearchingWithKeywordDataclass>


}

