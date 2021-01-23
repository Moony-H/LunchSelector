package com.example.lunchbox.manager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass

class RecyclerViewAdapter (private val searchedData: SearchingWithKeywordDataclass) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item =searchedData.documents[position]
        holder.placeName.text = item.place_name
        holder.address.text = item.road_address_name
    }

    override fun getItemCount(): Int = searchedData.meta.pageable_count


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.item_number)
        val address: TextView = view.findViewById(R.id.content)

    }


}