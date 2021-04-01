package com.example.lunchbox.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.staticMethod.StaticUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlin.system.exitProcess

class ResultActivity: AppCompatActivity() {
    private lateinit var resultPlace: Place
    private var backKeyPressedTime:Long=0
    private var longitude=0.0
    private var latitude=0.0
    private val onClickListener= View.OnClickListener {
        when(it.id){
            result_roulette_again.id->{
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            result_show_map.id->{
                val intent = Intent(this, ResultMapActivity::class.java)
                intent.putExtra("UserLongitude",longitude)
                intent.putExtra("UserLatitude",latitude)
                intent.putExtra("Place",resultPlace)
                startActivity(intent)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        StaticUtils.setFullScreenMode(this)

        //받아온거 세팅
        resultPlace= intent.getSerializableExtra("Place") as Place
        intent.extras?.let {
            longitude=it.getDouble("UserLongitude")
            latitude=it.getDouble("UserLatitude")

        }

        Log.d("Place", resultPlace.place_name )
        result_place_name.text="${resultPlace.place_name}\n${resultPlace.road_address_name}"

        result_roulette_again.setOnClickListener(onClickListener)
        result_show_map.setOnClickListener(onClickListener)
    }

    override fun onBackPressed() {
        //super.onBackPressed()

        //if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
        //    backKeyPressedTime = System.currentTimeMillis()
        //    Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
        //    return
        //}
//
        //if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
        //    ActivityCompat.finishAffinity(this)
//
        //    exitProcess(0)
        //}
    }
}