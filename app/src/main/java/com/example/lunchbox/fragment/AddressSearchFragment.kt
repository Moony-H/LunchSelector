package com.example.lunchbox.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lunchbox.R
import com.example.lunchbox.tags.POIItemTag
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass
import com.example.lunchbox.manager.RecyclerViewAdapter
import com.example.lunchbox.client.RestAPIClient

import net.daum.mf.map.api.MapView

class AddressSearchFragment(val mapview: MapView?): Fragment() {
    var listClicked:((Place)->Unit)?=null


    fun setListClickListener(doing:(Place)->Unit){
        listClicked=doing

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_search_list,container,false)

        //리사이클러뷰 세팅
        val recyclerView:RecyclerView=view.findViewById(R.id.Searched_list)
        recyclerView.layoutManager= LinearLayoutManager(context)

        //쿼리 리스너
        view.findViewById<SearchView>(R.id.LocationSearchView).setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    val restAPIClient= RestAPIClient()
                    val pin=mapview?.findPOIItemByTag(POIItemTag.PIN)

                    //맵의 핀의 위치 가져오기.
                    val x=pin?.mapPoint?.mapPointGeoCoord?.longitude
                    val y=pin?.mapPoint?.mapPointGeoCoord?.latitude

                    //핀이 둘다 있으면
                    if (x != null&& y!=null) {
                        //서버에 요청하여 위치 검색
                        restAPIClient.getFromKeyword(query,x,y,getString(R.string.api_key)) {
                            if(listClicked==null)
                                recyclerView.adapter=RecyclerViewAdapter(it)
                            else
                                recyclerView.adapter=RecyclerViewAdapter(it, listClicked!!)
                        }
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