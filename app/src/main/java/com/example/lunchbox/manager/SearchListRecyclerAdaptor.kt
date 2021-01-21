package com.example.lunchbox.manager

import android.R
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class SearchListRecyclerAdaptor:RecyclerView.Adapter<SearchListRecyclerAdaptor.ViewHolder>() {
    private val mData: ArrayList<String>? = null

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView1: TextView

        init {

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.text1)
        }
    }

}