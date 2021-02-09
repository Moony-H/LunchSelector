package com.example.lunchbox.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.example.lunchbox.Client.RestAPIClient
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.FoodTypeTag
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.dataclass.SearchingWithKeywordDataclass
import com.example.lunchbox.staticMethod.StaticUtils
import kotlinx.android.synthetic.main.activity_option.*
import java.util.Random


class OptionActivity : AppCompatActivity() {

    private val toggleFoodList = mutableListOf(false,false,false,false)
    private var radius=0
    private var longitude=0.0
    private var latitude=0.0
    private var address="Not Found"
    private var backKeyPressedTime:Long=0
    private var restAPIClient=RestAPIClient()
    private val toggleButtonListener= CompoundButton.OnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
        when(compoundButton.id){
            option_toggle_koreanFood.id->{
                toggleFoodList[FoodTypeTag.KOREANFOOD]=b
            }
            option_toggle_westernFood.id->{
                toggleFoodList[FoodTypeTag.WESTERNFOOD]=b
            }
            option_toggle_chineseFood.id->{
                toggleFoodList[FoodTypeTag.CHINESEFOOD]=b
            }
            option_toggle_japaneseFood.id->{
                toggleFoodList[FoodTypeTag.JAPANESEFOOD]=b
            }

        }

    }

    private fun randomRoulette(list: List<SearchingWithKeywordDataclass>):Place{
        val random= Random()
        val num=random.nextInt(list.size)
        val docRandomNum=random.nextInt(list[num].documents.size)
        return list[num].documents[docRandomNum]
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        StaticUtils.setFullScreenMode(this)

        //intent 받아오기
        intent.getStringExtra("Radius")?.let { radius=it.toInt() }
        intent.getStringExtra("Longitude")?.let { longitude=it.toDouble() }
        intent.getStringExtra("Latitude")?.let { latitude=it.toDouble() }
        intent.getStringExtra("Address")?.let { address= it }

        //받아온거 세팅

        option_address_text.text=address
        option_range_editText.setText(radius.toString())


        //토글 버튼 리스너 세팅
        option_toggle_koreanFood.setOnCheckedChangeListener(toggleButtonListener)
        option_toggle_westernFood.setOnCheckedChangeListener(toggleButtonListener)
        option_toggle_chineseFood.setOnCheckedChangeListener(toggleButtonListener)
        option_toggle_japaneseFood.setOnCheckedChangeListener(toggleButtonListener)

        //스타트 버튼 리스너 세팅
        option_selecting_start_button.setOnClickListener{
            if(true in toggleFoodList) {
                val foodList = mutableListOf<String>()
                val results = mutableListOf<SearchingWithKeywordDataclass>()
                var repeat = 0
                for (i in toggleFoodList.indices) {
                    if (toggleFoodList[i])
                        foodList.add(FoodTypeTag.getKoreanStringWithFoodTypeTag(i))
                }
                restAPIClient.getFromKeyword(
                    foodList,
                    longitude,
                    latitude,
                    getString(R.string.api_key),
                    radius
                ) {
                    results.add(it)
                    if (results.size >= foodList.size) {
                        val randomResult = randomRoulette(results)
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra("Place", randomResult)
                        startActivity(intent)
                    }


                }
            }
            else
                Toast.makeText(this,"음식 종류를 한가지 이상 선택해 주세요.",Toast.LENGTH_SHORT).show()


        }



        

    }
}