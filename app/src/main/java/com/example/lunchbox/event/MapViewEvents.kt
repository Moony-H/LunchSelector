package com.example.lunchbox.event

import android.app.Activity
import android.util.Log
import android.widget.TextView
import com.example.lunchbox.R
import com.example.lunchbox.manager.LocationManager
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView
import java.util.*
import kotlin.concurrent.timer



class MapViewEvents(private val myLocationManager: LocationManager,
                    private val marker:MapPOIItem,
                    private val activity:Activity,
                    private val localAPIKey:String,
                    private val textView: TextView): MapView.MapViewEventListener {



    private lateinit var goalMapPoint:MapPoint
    private  var timer: Timer?=null

    private var isTracking=true
    private var mapview:MapView?=null


    private val reverseGeoCodingListener=MapReverseLocation {
        textView.text=it
    }


    //기준 위치 변경
    private fun setGoalLocation(mapPoint: MapPoint){
        goalMapPoint=mapPoint
        val reverseGeoCoder=MapReverseGeoCoder(localAPIKey,mapPoint,reverseGeoCodingListener,activity)
        reverseGeoCoder.startFindingAddress()
    }


    //현재 사용자의 실제 위치로 변경
    private fun setMyLocation() {
        setGoalLocation(MapPoint.mapPointWithGeoCoord(myLocationManager.latitude, myLocationManager.longitude))
    }




    //설정된 위치로 이동.
    private fun goToLocation(){
        mapview?.setMapCenterPoint(goalMapPoint,true)

    }


    //외부에서 사용할 내 위치로 이동 버튼
    fun goToMyLocation(){
        isTracking=true
        setMyLocation()
        goToLocation()
        initPin()
        mapview?.removeAllCircles()
    }





    //외부에서 사용할 아무 위치로 이동
    fun goToCustomLocation(x:Double,y:Double){
        Log.e("hey","GoToCustom")
        isTracking=false
        setGoalLocation(MapPoint.mapPointWithGeoCoord(y,x))
        goToLocation()
        initTimer()
        initPin()
        mapview?.removeAllCircles()
    }

    //외부에서 쓰레드 스탑.
    fun timerStop(){
        timer?.cancel()
    }










    private fun initTimer(){

        timer?.cancel()
        timerStart()

    }

    private fun initPin(){
        mapview?.removePOIItem(marker)
        marker.mapPoint = goalMapPoint
        mapview?.addPOIItem(marker)
    }

    fun timerStart() {

        timer = timer(period = 3000, initialDelay = 3000) {
            if(isTracking){
                setMyLocation()
            }
            goToLocation()
            initPin()
            Log.e("timer","Running")
        }


    }

    override fun onMapViewInitialized(p0: MapView?) {
        Log.e("Map", "Initialized")
        mapview=p0
        setGoalLocation(MapPoint.mapPointWithGeoCoord(myLocationManager.latitude, myLocationManager.longitude))
        goToLocation()
        timerStart()
        initPin()

    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Tuched")
        isTracking=false
        if (p1 != null) {
            setGoalLocation(p1)
        }
        initPin()
        goToLocation()
        initTimer()
        mapview?.removeAllCircles()
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Double_Tapped")

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Drag_Start")
        timer?.cancel()

    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Drag End")
        initTimer()
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Map_Move_Finished")
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Center_Moved")
        //initTimer()
    }




    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        initTimer()
        Log.e("Map","LongPressed")
    }


    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        initTimer()
    }
}