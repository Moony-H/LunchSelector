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
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass
import com.example.lunchbox.manager.MapViewEvents
import com.example.lunchbox.manager.RecyclerViewAdapter
import com.example.lunchbox.manager.RestAPIClient
import kotlinx.android.synthetic.main.fragment_search_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddressSearchFragment(): Fragment() {
    private var mapViewEvents:MapViewEvents?=null
    var listClicked:((Place)->Unit)?=null


    fun setListClickListener(doing:(Place)->Unit){
        listClicked=doing

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_search_list,container,false)

        //리사이클러뷰 세팅
        val recyclerView:RecyclerView=view.findViewById(R.id.Searched_list)
        recyclerView.layoutManager= LinearLayoutManager(context)
        view.findViewById<SearchView>(R.id.LocationSearchView).setOnQueryTextListener(object :SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    val restAPIClient=RestAPIClient()

                    restAPIClient.getFromKeyword(query,126.97942,37.592128000000002,getString(R.string.api_key)) { data: SearchingWithKeywordDataclass ->
                        if(listClicked==null)
                            recyclerView.adapter=RecyclerViewAdapter(data)
                        else
                            recyclerView.adapter=RecyclerViewAdapter(data, listClicked!!)
                    }

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