package com.example.lunchbox.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.staticMethod.StaticUtils
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity: AppCompatActivity() {
    private lateinit var resultPlace: Place

    val onClickListener= View.OnClickListener {
        when(it.id){
            result_roulette_again.id->{
                StaticUtils.intentManger(this,MainActivity::class.java)
            }
            result_show_map.id->{

            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        StaticUtils.setFullScreenMode(this)
        resultPlace= intent.getSerializableExtra("Place") as Place
        Log.d("Place", resultPlace.place_name)
        result_place_name.text="${resultPlace.place_name}\n${resultPlace.road_address_name}"

        result_roulette_again.setOnClickListener(onClickListener)
        result_show_map.setOnClickListener(onClickListener)
    }
}