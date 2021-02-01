package com.example.lunchbox.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass
import com.example.lunchbox.manager.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_search_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddressSearchFragment:Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {  }


    }

    private fun clientStart(view:View,Keyword:String,x:Double,y:Double){
        val baseURL=getString(R.string.restapi_url)
        val apiKey="KakaoAK "+getString(R.string.api_key)


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
        val api=retrofit.create(RestAPIClient::class.java)
        //val call = api.getFromKeyword(apiKey,Keyword,x.toString(),y.toString(),"distance")   // 검색 조건 입력
        val call = api.getFromKeyword(apiKey,Keyword,126.9784147,37.5666805,"distance")
        // API 서버에 요청
        call.enqueue(object: Callback<SearchingWithKeywordDataclass> {
            override fun onResponse(
                call: Call<SearchingWithKeywordDataclass>,
                response: Response<SearchingWithKeywordDataclass>
            ) {
                val recyclerView=view.findViewById<RecyclerView>(R.id.Searched_list)
                if(recyclerView is RecyclerView){
                    Log.e("View is","RecyclerView")
                    response.body()?.let {
                        val myRecyclerViewAdapter=RecyclerViewAdapter(it)
                        myRecyclerViewAdapter.notifyDataSetChanged()
                        recyclerView.layoutManager=LinearLayoutManager(context)
                        recyclerView.adapter= myRecyclerViewAdapter
                    }

                }
                else{
                    Log.e("View is","not RecyclerView")
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
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_search_list,container,false)

        //리사이클러뷰 세팅
        val recyclerView:RecyclerView=Searched_list
        recyclerView.layoutManager= LinearLayoutManager(context)
        view.findViewById<SearchView>(R.id.LocationSearchView).setOnQueryTextListener(object :SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{clientStart(view,query,126.97942,37.592128000000002)


                return true }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })

        return view
    }
}