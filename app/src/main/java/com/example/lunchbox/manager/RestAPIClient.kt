package com.example.lunchbox.manager

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestAPIClient() {
    private val baseURL="https://dapi.kakao.com/"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    private val api=retrofit.create(RestAPIClientInterface::class.java)






    fun getFromKeyword(Keyword:String, x:Double, y:Double,apiKey:String,doing:(SearchingWithKeywordDataclass)->Unit){

        val call = api.getFromKeyword(apiKey,Keyword,x,y,"distance")
        call.enqueue(object: Callback<SearchingWithKeywordDataclass> {
            override fun onResponse(
                call: Call<SearchingWithKeywordDataclass>,
                response: Response<SearchingWithKeywordDataclass>
            ) {


                response.body()?.let {
                    doing(it)
                }

                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("Test", "Raw: ${response.raw()}")
                Log.d("Test", "Body: ${response.body()}")
            }

            override fun onFailure(call: Call<SearchingWithKeywordDataclass>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
    }


}