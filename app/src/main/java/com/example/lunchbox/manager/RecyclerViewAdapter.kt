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

class RecyclerViewAdapter (private val searchedData: SearchingWithKeywordDataclass) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycle, parent, false)
        Log.d("onCreate","done")

        return ViewHolder(view,searchedData)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item =searchedData.documents[position]
        holder.placeName.text = item.place_name
        holder.address.text = item.road_address_name
        holder.data=item
    }

    override fun getItemCount(): Int = searchedData.meta.pageable_count


    inner class ViewHolder(view: View, data: SearchingWithKeywordDataclass) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.place_title)
        val address: TextView = view.findViewById(R.id.place_address)
        var data:Place?=null
        init {
            view.setOnClickListener{

            }
        }

    }


}