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

class RestAPIClient(baseURL: String) {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
    val api=retrofit.create(RestAPIClientInterface::class.java)






    fun getFromKeyword(recyclerView: RecyclerView, Keyword:String, x:Double, y:Double,apiKey:String){

        val call = api.getFromKeyword(apiKey,Keyword,x,y,"distance")
        call.enqueue(object: Callback<SearchingWithKeywordDataclass> {
            override fun onResponse(
                call: Call<SearchingWithKeywordDataclass>,
                response: Response<SearchingWithKeywordDataclass>
            ) {


                Log.e("View is","RecyclerView")
                response.body()?.let {
                //val myRecyclerViewAdapter=RecyclerViewAdapter(it)
                //myRecyclerViewAdapter.notifyDataSetChanged()
                //recyclerView.layoutManager= LinearLayoutManager(context)
                //recyclerView.adapter= myRecyclerViewAdapter
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