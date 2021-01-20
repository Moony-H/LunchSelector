package com.example.lunchbox

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_intro.*
import kotlin.concurrent.timer


class IntroActivity : AppCompatActivity() {
    val Intro_Loading= listOf<String>("ㅁ","무","뭐","뭠","뭐머","뭐먹","뭐먹ㅈ","뭐먹지","뭐먹지.","뭐먹지..","뭐먹지...")

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        var repeat=0
        var finish=0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)


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
                    cancel();startActivity(Intent(this@IntroActivity,MainActivity::class.java))
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