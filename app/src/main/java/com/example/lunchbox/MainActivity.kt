package com.example.lunchbox

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import kotlin.concurrent.timer


class MainActivity : AppCompatActivity() {

    var mapview:MapView?=null
    private var backKeyPressedTime:Long=0
    var latitude:Double=37.592128000000002//위도
    var longitude:Double=126.97942//경도
    var prevlatitude:Double=0.0
    var prevlongitude:Double=0.0
    var myLocationManager:LocationManagement?=null
    var trackingStop=false
    val customMapViewEvents= MapViewEvents()
    fun locationChanged():Boolean{
        if(prevlatitude!=latitude || prevlongitude!=longitude)
        {
            prevlatitude=latitude
            prevlongitude=longitude
            return true
        }
        return false
    }
    fun test(){
        mapview?.mapCenterPoint?.mapPointGeoCoord?.latitude
        mapview?.mapCenterPoint?.mapPointGeoCoord?.longitude
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
        mapview?.setMapViewEventListener(customMapViewEvents)



        

    }

    override fun onResume() {
        super.onResume()
        var marker=MapPOIItem()
        marker.itemName="Test_Marker"
        marker.tag=0
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude,longitude)
        marker.markerType=MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType=MapPOIItem.MarkerType.RedPin


        if(myLocationManager!!.CheckLocationPermission()){
            myLocationManager?.LocationListenerSetting()
            myLocationManager?.LocationUpdateSetting(5000,5f)
        }


        timer(period = 3000) {
            longitude=myLocationManager!!.longitude
            latitude=myLocationManager!!.latitude
            runOnUiThread{
                mapview?.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude),true)
                if(locationChanged()){
                    mapview?.removePOIItem(marker)
                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude,longitude)
                    mapview?.addPOIItem(marker)
                }


            }
            if(trackingStop){
                cancel()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        myLocationManager?.LocationListenerStop()
    }
}