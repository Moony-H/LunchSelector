package com.example.lunchbox.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lunchbox.R
import com.example.lunchbox.staticMethod.StaticUtils


class OptionActivity : AppCompatActivity() {

    private var backKeyPressedTime:Long=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        StaticUtils.setFullScreenMode(this)

        

    }
}