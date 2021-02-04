package com.example.lunchbox.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.FoodTypeTag
import com.example.lunchbox.staticMethod.StaticUtils
import kotlinx.android.synthetic.main.activity_option.*


class OptionActivity : AppCompatActivity() {

    private val toggleFoodList = mutableListOf(false,false,false,false)
    private var radius=0.0
    private var longitude=0.0
    private var latitude=0.0
    private var backKeyPressedTime:Long=0
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

    private fun randomRoulette(){

    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        StaticUtils.setFullScreenMode(this)

        //intent 받아오기
        intent.getStringExtra("Radius")?.let { radius=it.toDouble() }
        intent.getStringExtra("Longitude")?.let { longitude=it.toDouble() }
        intent.getStringExtra("Latitude")?.let { latitude=it.toDouble() }


        //토글 버튼 리스너 세팅
        option_toggle_koreanFood.setOnCheckedChangeListener(toggleButtonListener)
        option_toggle_westernFood.setOnCheckedChangeListener(toggleButtonListener)
        option_toggle_chineseFood.setOnCheckedChangeListener(toggleButtonListener)
        option_toggle_japaneseFood.setOnCheckedChangeListener(toggleButtonListener)

        //스타트 버튼 리스너 세팅
        option_selecting_start_button.setOnClickListener{
            val foodList= mutableListOf<String>()
            val puts= mutableMapOf<String,String>()
            for(i in toggleFoodList.indices){
                if(toggleFoodList[i])
                    foodList.add(FoodTypeTag.getKoreanStringWithFoodTypeTag(i))
            }
            puts.put("radius",)
            StaticUtils.intentManger(this,ResultActivity::class.java,"FoodList", foodList as ArrayList<String>)

        }



        

    }
}