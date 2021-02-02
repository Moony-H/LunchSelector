package com.example.lunchbox.manager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass

class RecyclerViewAdapter () : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var searchedData:SearchingWithKeywordDataclass?=null
    private var onViewHolderClicked:(()->Place)?=null

    //데이터만 표시
    constructor(searchedData: SearchingWithKeywordDataclass?) : this() {
        this.searchedData=searchedData
    }

    //데이터랑 데이터 클릭 했을 시 사용
    constructor(searchedData: SearchingWithKeywordDataclass?,onViewHolderClicked:()->Place) : this(searchedData) {
        this.searchedData=searchedData
        this.onViewHolderClicked=onViewHolderClicked
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycle, parent, false)
        Log.d("onCreate","done")
        return if (searchedData!=null)
                ViewHolder(view, searchedData!!)
            else
                ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        searchedData?.let{
            val item = it.documents[position]
            holder.placeName.text = item.place_name
            holder.address.text = item.road_address_name
        }

    }

    override fun getItemCount(): Int{
        return searchedData?.documents?.size ?: 0
    }




    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        constructor(view: View, data: SearchingWithKeywordDataclass) : this(view) {

        }
        val placeName: TextView = view.findViewById(R.id.place_title)
        val address: TextView = view.findViewById(R.id.place_address)
        init {
            view.setOnClickListener{
                onViewHolderClicked

            }
        }

    }


}