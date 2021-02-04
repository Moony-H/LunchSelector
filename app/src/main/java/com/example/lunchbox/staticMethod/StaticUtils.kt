package com.example.lunchbox.staticMethod

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.WindowManager
import com.example.lunchbox.dataclass.pair

class StaticUtils {
    companion object{

        fun intentManger(nowActivity: Context,nextActivity:Class<*>) {
            val intent= Intent(nowActivity,nextActivity)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            nowActivity.startActivity(intent)
        }

        fun intentManger(nowActivity: Context,nextActivity:Class<*>,listKey:String,putList:ArrayList<String>) {
            val intent= Intent(nowActivity,nextActivity)
            intent.putExtra(listKey,putList)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            nowActivity.startActivity(intent)
        }

        fun setFullScreenMode(activity: Activity) {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        }

    }
}