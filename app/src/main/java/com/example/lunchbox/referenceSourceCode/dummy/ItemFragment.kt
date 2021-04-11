package com.example.lunchbox.referenceSourceCode.dummy

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lunchbox.R

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //arguments가 Null이 아니면, 즉 무엇인가 인자를 주었으면 그 인자를 가져옴.
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //setContentView와 비슷한 것. 레이아웃을 객체화(프래그먼트는 액티비티에 소속 되어 있어야 하기 때문에, 레이아웃 말고라도 뷰 그룹이나(container) 다른 인자도 필요함.)
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        // Set the adapter, View가 RecycleView일 경우, 그리고 RecycleView로 캐스팅.
        if (view is RecyclerView) {
            with(view) {
                //뷰의 레이아웃 매니저의 속성 선택
                layoutManager = when {// when 은 매개변수(?)가 없으면 Switch 가 아닌 조건식을 달 수 있음
                    //칼럼이 하나 이거나 없으면 리니어 레이아웃.
                    columnCount <= 1 -> LinearLayoutManager(context)
                    //2개 이상이면 그리드 레이아웃.
                    else -> GridLayoutManager(context, columnCount)
                }
                //리사이클 뷰에 어댑터 설정.(자신이 어댑터를 override 해서 구현 해야함.(콜백인듯?))
                adapter = MyItemRecyclerViewAdapter(DummyContent.ITEMS)
            }
        }
        return view
    }
    //static 과 같은 방식. 어디서든지 접근하여 프래그먼트에 리스트 전달 가능. 정보는 저장뿐 아니라 표현하고 읽어야 의미가 있는데, 이러한 메소드 들도 역시 같이 static 으로 하는게 좋을듯 하다. 프래그먼트에 한데 작성하는 것이 좋은듯.
    companion object {
        //리스트에 아이템이 몇개나 있는지 알려주기 위한 값의 키 값.(Intent 키 값과 동일)
        const val ARG_COLUMN_COUNT = "column-count"

        //어디든지 접근 가능한 함수로, 이 프래그먼트에 리스트 값을 줄 수 있음.
        //자바에서 사용할 때에 이 함수가 Static 메소드라는 것을 알려 주어야 함. 그래서 어노테이션 사용.
        @JvmStatic
        fun newInstance(columnCount: Int) =
            //ItemFragment.arguments=Bundle().putInt(ARG_COLUMN_COUNT, columnCount) 와 동일
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}