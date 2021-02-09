package com.example.lunchbox.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.staticMethod.StaticUtils

class ResultActivity: AppCompatActivity() {
    private lateinit var resultPlace: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        StaticUtils.setFullScreenMode(this)
        resultPlace= intent.getSerializableExtra("Place") as Place
        Log.d("Place", resultPlace.place_name)

    }
}