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
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.POIItemTag
import com.example.lunchbox.dataclass.PinData
import com.example.lunchbox.fragment.AddressSearchFragment
import com.example.lunchbox.manager.LocationManager
import com.example.lunchbox.manager.MapViewEvents
import com.example.lunchbox.manager.POIEvents
import com.example.lunchbox.staticMethod.StaticUtils
import kotlinx.android.synthetic.main.activity_main.*
import net.daum.mf.map.api.*


class MainActivity : AppCompatActivity() {
    private var isFragmentOpened=false
    var mapview:MapView?=null
    private var marker:PinData?=null
    var customPOIEvents: POIEvents?=null
    private var backKeyPressedTime:Long=0
    var latitude:Double=37.592128000000002//위도
    var longitude:Double=126.97942//경도
    var myLocationManager: LocationManager?=null
    var customMapViewEvents: MapViewEvents?=null
    var circle:MapCircle?=null

    private fun checkRadiusLimits(radius: Int):Int{
        return if (radius>3000)
            3000
        else if(radius<0)
            100
        else
            radius

    }

    private val clickListener = View.OnClickListener {
        when(it.id){

            //현재 내 위치로 버튼
            myLocationButton.id-> {
                Log.e("Location", "Location Button Downed")
                customMapViewEvents?.goToMyLocation()
            }

            //프래그먼트 소환 버튼
            search_button.id->{
                Log.d("Button","Search_Button Downed")
                if(!isFragmentOpened)
                {
                    ObjectAnimator.ofFloat(Search_Frame,"TranslationX",500f).apply { duration=1000
                        start()}
                    isFragmentOpened=true
                }
            }

            //반지름 제출 버튼
            radius_submit_button.id->{
                Log.d("Button","radius Submit button Downed")

                //원 지우기
                mapview?.removeAllCircles()

                //텍스트를 받아와 인트로 바꾸고 반지름 리미트 설정
                val text= radius_edit_text.text.toString()
                var radius=text.toInt()
                radius=checkRadiusLimits(radius)



                //원을 핀의 위치에 추가.
                circle?.radius=radius
                circle?.center=getPinLocation()
                mapview?.addCircle(circle)


                //원이 전부 표시되게 줌 레벨 변경
                val mapPointBounds:MapPointBounds= circle!!.bound
                mapview?.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds,50))
            }

            //옵션으로 넘어가기 버튼
            main_start_button.id->{
                Log.d("Button","Go to Option")
                val longitude=getPinLocation()?.mapPointGeoCoord?.longitude.toString()
                val latitude=getPinLocation()?.mapPointGeoCoord?.latitude.toString()
                StaticUtils.intentManger(this,OptionActivity::class.java, mapOf("Radius" to circle?.radius.toString(),"Longitude" to longitude,"Latitude" to latitude))
            }

        }

    }

    private fun getPinLocation():MapPoint?{
        val pin=mapview?.findPOIItemByTag(POIItemTag.PIN)
        return pin?.mapPoint

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
        StaticUtils.setFullScreenMode(this)


        //위치 데이터 리스트


        //맵을 뷰에 추가
        val viewGroup = Map_layout
        mapview=MapView(this)
        viewGroup.addView(mapview)

        //마커 세팅
        val basicMarker=MapPOIItem()
        basicMarker.itemName="Test_Marker"
        basicMarker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude,longitude)
        basicMarker.markerType=MapPOIItem.MarkerType.BluePin
        basicMarker.selectedMarkerType=MapPOIItem.MarkerType.RedPin
        basicMarker.tag=POIItemTag.PIN
        marker?.pin=basicMarker

        //써클의 기본 세팅
        circle=MapCircle(
            mapview?.mapCenterPoint,  // center
            500,  // 미터 단위
            ContextCompat.getColor(this, R.color.ThemeSubColor),
            ContextCompat.getColor(this, R.color.ThemeColor)// fillColor
        )
        circle?.tag=POIItemTag.CIRCLE

        //위치추적 매니저 생성.
        myLocationManager= LocationManager(this)

        //맵 이벤트 생성
        customMapViewEvents= MapViewEvents( myLocationManager!!,marker?.pin)









        //POI이벤트 생성
        //customPOIEvents= POIEvents(circle!!, rangePoint!!)


        //프래그먼트 추가
        val fragmentManager=supportFragmentManager
        val searchFragment= AddressSearchFragment(mapview!!)





        //프래그먼트에 리스트 클릭시 할 행동 추가.
        searchFragment.setListClickListener {
            customMapViewEvents!!.goToCustomLocation(it.x,it.y)
        }
        val transaction=fragmentManager.beginTransaction()
        transaction.replace(R.id.Search_Frame,searchFragment).commitAllowingStateLoss()


        //버튼 리스너 세팅
        myLocationButton.setOnClickListener(clickListener)
        search_button.setOnClickListener(clickListener)
        Map_layout.setOnClickListener(clickListener)
        radius_submit_button.setOnClickListener(clickListener)


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