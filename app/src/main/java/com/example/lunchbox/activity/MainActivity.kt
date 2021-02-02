package com.example.lunchbox.activity


import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.lunchbox.R
import com.example.lunchbox.manager.LocationManager
import com.example.lunchbox.manager.MapViewEvents
import com.example.lunchbox.manager.POIEvents
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class MainActivity : AppCompatActivity() {
    private var isFragmentOpened=false
    var mapview:MapView?=null
    var marker=MapPOIItem()
    var customPOIEvents: POIEvents?=null
    var rangePoint:MapPOIItem?=null
    private var backKeyPressedTime:Long=0
    var latitude:Double=37.592128000000002//위도
    var longitude:Double=126.97942//경도
    var myLocationManager: LocationManager?=null
    var customMapViewEvents: MapViewEvents?=null
    var circle:MapCircle?=null
    private val clickListener = View.OnClickListener {
        when(it.id){
            myLocationButton.id-> {
                Log.e("Location", "Location Button Downed")
                customMapViewEvents?.goToMyLocation()
            }
            search_button.id->{
                Log.d("Button","Search_Button Downed")
                if(!isFragmentOpened)
                {
                    ObjectAnimator.ofFloat(Search_Frame,"TranslationX",500f).apply { duration=1000
                        start()}
                    isFragmentOpened=true
                }
            }

        }

    }



    enum class POIItemNumber(val number:Int) {
        PICKMARKER(0),RANGEPOINT(1),CIRCLE(2)
    }








    override fun onBackPressed() {
        //super.onBackPressed()
        var toast:Toast?
        if(isFragmentOpened)
        {
            isFragmentOpened=false
            ObjectAnimator.ofFloat(Search_Frame,"TranslationX",-500f).apply { duration=1000
                start()}
        }
        else {
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
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resources.getColor(R.color.ThemeColor)
        //전체화면모드 활성화
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)


        //위치 데이터 리스트


        //맵을 뷰에 추가
        val viewGroup = Map_layout
        mapview=MapView(this)
        viewGroup.addView(mapview)


        //마커 세팅
        marker=MapPOIItem()
        marker.itemName="Test_Marker"
        marker.tag= POIItemNumber.PICKMARKER.number
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude,longitude)
        marker.markerType=MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType=MapPOIItem.MarkerType.RedPin




        //위치추적 매니저 생성.
        myLocationManager= LocationManager(this)

        //맵 이벤트 생성
        customMapViewEvents= MapViewEvents( myLocationManager!!,marker)




        //써클의 기본 세팅
        circle=MapCircle(
            mapview?.mapCenterPoint,  // center
            500,  // radius
            ContextCompat.getColor(this, R.color.ThemeColor),
            ContextCompat.getColor(this, R.color.ThemeSubColor)// fillColor
        )
        circle?.tag= POIItemNumber.CIRCLE.number

        //******드래그 가능한 원의 포인트 세팅******
        rangePoint=MapPOIItem()
        rangePoint?.isDraggable=true
        rangePoint?.markerType=MapPOIItem.MarkerType.CustomImage
        rangePoint?.customImageResourceId= R.drawable.question
        rangePoint?.setCustomImageAnchor(0.5f, 0.5f)
        rangePoint?.itemName="rangePoint"
        rangePoint?.tag= POIItemNumber.RANGEPOINT.number


        //POI이벤트 생성
        customPOIEvents= POIEvents(circle!!, rangePoint!!)


        //프래그먼트에 맵뷰 주기.
        val bundle=Bundle(1)
        bundle.putSerializable("mapview",customMapViewEvents)
        search_fragment.arguments=bundle


        //버튼 리스너 세팅
        myLocationButton.setOnClickListener(clickListener)
        search_button.setOnClickListener(clickListener)
        Map_layout.setOnClickListener(clickListener)
        //SearchView 리스너 세팅

    }


    override fun onResume() {
        super.onResume()

        //마커 생성



        //위치 권한 체크 밑 위치추적 활성화
        if(myLocationManager!!.checkLocationPermission()){
            myLocationManager?.locationListenerSetting()
            myLocationManager?.locationUpdateSetting(5000,0.1f)
        }


        //맵뷰에 이벤트 추가
        mapview?.setMapViewEventListener(customMapViewEvents)
        mapview?.setPOIItemEventListener(customPOIEvents)


    }

    override fun onPause() {
        super.onPause()
        myLocationManager?.StopLocationListener()
        mapview?.removeAllPOIItems()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        myLocationManager?.StopLocationListener()
    }
}