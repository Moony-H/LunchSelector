package com.example.lunchbox.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.lunchbox.R
import com.example.lunchbox.staticMethod.StaticUtils
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_intro.*
import net.daum.android.map.util.URLEncoder.encode
import java.util.*
import kotlin.concurrent.timer


class IntroActivity : AppCompatActivity() {
    val Intro_Loading= listOf<String>(
        "ㅁ",
        "무",
        "뭐",
        "뭠",
        "뭐머",
        "뭐먹",
        "뭐먹ㅈ",
        "뭐먹지",
        "뭐먹지.",
        "뭐먹지..",
        "뭐먹지..."
    )

    override fun onBackPressed() {
        //super.onBackPressed()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        var repeat=0
        var finish=0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        StaticUtils.setFullScreenMode(this)


        timer(period = 300)
        {
            runOnUiThread{
                intro_Text.setText(Intro_Loading[repeat])
            }

            repeat++
            if(repeat==Intro_Loading.size){
                repeat-=3
                finish++
                if(finish==3)
                {
                    cancel();StaticUtils.intentManger(this@IntroActivity, MainActivity::class.java)

                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
    }
}