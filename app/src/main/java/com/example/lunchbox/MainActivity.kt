package com.example.lunchbox

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {
    var mapview:MapView?=null
    var marker=MapPOIItem()
    private var backKeyPressedTime:Long=0
    var latitude:Double=37.592128000000002//위도
    var longitude:Double=126.97942//경도
    var myLocationManager:LocationManagement?=null
    var customMapViewEvents:MapViewEvents?=null
    val clickListener = View.OnClickListener {
        when(it.id){
            myLocationButton.id->{Log.e("Location","Location Button Downed")
                customMapViewEvents?.allStop=false}
        }

    }
    enum class POIItemNumber(val number:Int) {
        PICKMARKER(0),RANGEPOINT(2)
    }
    inner class AxisPoint(){
        var xAxis:Double=0.0
        var yAxis:Double=0.0
    }




    override fun onBackPressed() {
        //super.onBackPressed()
        var toast:Toast?
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis()
            toast = Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG)
            toast.show()
            return
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            ActivityCompat.finishAffinity(this)

            System.exit(0)
            
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility=(View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        //이유는 모르지만 무조건 객체를 생성할 때, 클래스 필드로 생성하면 안댄다 ㅠㅠ

        myLocationManager= LocationManagement(this)




        val viewGroup = Main_layout
        //이유는 모르지만 맵 뷰는 항상 onCreate에 있어야 한다.
        mapview=MapView(this)
        viewGroup.addView(mapview)
        customMapViewEvents= MapViewEvents(this, myLocationManager!!,marker)
        myLocationButton.bringToFront()
        LocationSearchBar.bringToFront()
        myLocationButton.setOnClickListener(clickListener)
        val circle=MapCircle(MapPoint.mapPointWithGeoCoord(37.537094, 127.005470), // center
            10, // radius
            R.color.ThemeSubColor, // strokeColor
            R.color.ThemeColor)// fillColor


    }


    override fun onResume() {
        super.onResume()
        marker.itemName="Test_Marker"
        marker.tag=POIItemNumber.PICKMARKER.number
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude,longitude)
        marker.markerType=MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType=MapPOIItem.MarkerType.RedPin
        marker.isDraggable


        if(myLocationManager!!.CheckLocationPermission()){
            myLocationManager?.LocationListenerSetting()
            myLocationManager?.LocationUpdateSetting(5000,0.1f)
        }

        mapview?.setMapViewEventListener(customMapViewEvents)
        //mapview?.setPOIItemEventListener()






    }

    override fun onStop() {
        super.onStop()
        myLocationManager?.StopLocationListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        myLocationManager?.StopLocationListener()
    }
}