package com.example.lunchbox.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import com.example.lunchbox.client.RestAPIClient
import com.example.lunchbox.R
import com.example.lunchbox.tags.FoodTypeTag
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.tags.SearchTag
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

    private fun randomRoulette(list: List<SearchingWithKeywordDataclass>):Place?{

        val newList= mutableListOf<Place>()
        //Place 추출.
        for(i in list){
            newList.addAll(i.documents)
        }
        return if(newList.size>0){
            val random= Random()
            val num=random.nextInt(newList.size)
            newList[num]
        } else
            null

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

                //사용자가 한가지의 태그라도 선택했을 경우,
                val foodList = mutableListOf<String>()
                val results = mutableListOf<SearchingWithKeywordDataclass>()
                for (i in toggleFoodList.indices) {


                    //true, false 검사해서 사용자가 무슨 음식 종류를 선택했는지 검사, 스트링 추가.
                    if (toggleFoodList[i])
                        foodList.add(FoodTypeTag.getKoreanStringWithFoodTypeTag(i))
                }

                //카카오 RestAPI 사용하여 음식점 태그가 있는 음식점들 검사해서 가져오기.(리스트의 길이 만큼 반복)
                restAPIClient.getFromKeyword(foodList, longitude, latitude, getString(R.string.api_key), radius, SearchTag.Food) {
                    results.add(it)
                    if (results.size >= foodList.size) {

                        //다 되었을 경우
                        val randomResult = randomRoulette(results)

                        if(randomResult ==null){

                            //음식점이 없을 경우,
                            Toast.makeText(this,"주위에 음식점이 없네요 ㅠㅠ 뒤로가기를 눌러 다시 선택해 주세요.",Toast.LENGTH_SHORT).show()
                        }
                        else{

                            //음식점이 있을 경우
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra("Place", randomResult)
                            intent.putExtra("UserLongitude",longitude)
                            intent.putExtra("UserLatitude",latitude)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }

                    }


                }
            }
            else
                Toast.makeText(this,"음식 종류를 한가지 이상 선택해 주세요.",Toast.LENGTH_SHORT).show() //사용자가 아무 태그도 선택하지 않았을 경우


        }



        

    }
}