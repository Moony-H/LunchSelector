package com.example.lunchbox.referenceSourceCode.dummy

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.lunchbox.R

import com.example.lunchbox.referenceSourceCode.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */

//adapter 는 뷰(List 요소)를 생성하는 클래스. 오버라이드 하는데 콜백인듯?(인터페이스가 아니라 아닌가?)
class MyItemRecyclerViewAdapter(private val values: List<DummyItem>) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //특정 xml 파일을 클래스로 변환(그래서 context 필요)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        //변환한 뷰를 뷰 홀더에 넣고 반환.
        return ViewHolder(view)
    }
    //뷰 홀더가 재사용 될때 사용되는 메서드.
    //리사이클 뷰는 말 그대로 재활용을 하는데, 사용자가 스크롤을 내릴 때 위의 목록은 가려지고, 새로운 목록을 보여주는데, 이 때 새로 생성하지 않고 가려지는 목록을 재 사용하여 내용을 바꾸어 새 목록을 보여준다.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //데이터 클래스인 values(private 이고, 생성자에 받아온 매개변수)의 List 요소 중 position 번째 값을  item 이라고 명명.
        val item = values[position]
        holder.idView.text = item.id //홀더의(밑의 inner class)텍스트 뷰에 내가 만든 데이터 리스트의 id를 넣는다.
        holder.contentView.text = item.content//마찬가지로 내가 만든 데이터 리스트의 내용을 넣는다.
    }

    override fun getItemCount(): Int = values.size

    //내가 지정한 형식으로 리스트 요소를 동적으로 넣어야 하기 때문에 나의 상황에 맞게 val을 짜야한다.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)// 넣을 리스트 요소의 TextView. findViewById로 찾는 이유는 액티비티와는 다르게 연결이 안되서 인듯.
        val contentView: TextView = view.findViewById(R.id.content)// 위와 마찬가지.


        //이건 왜 하는지 모르겠다... 어따쓰지...
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"// 일단 위의 contentView 필드를 추가 해서 뭔가 수정하여 어떻게 하는듯.
        }
    }
}