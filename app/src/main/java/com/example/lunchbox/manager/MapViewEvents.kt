package com.example.lunchbox.manager

import android.app.Activity
import android.util.Log
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*
import kotlin.concurrent.timer



class MapViewEvents(val myLocationManager: LocationManagement,val marker:MapPOIItem): MapView.MapViewEventListener {
    var timer: Timer? = null
    var allStop = false
    var mapview:MapView?=null



    private fun GoToMyLocation(Point:MapPoint=MapPoint.mapPointWithGeoCoord(myLocationManager.latitude, myLocationManager.longitude)) {
        mapview?.setMapCenterPoint(Point,true)

    }
    fun StopTracking(){allStop=true}

    fun StartTracking(){
        allStop=false
        GoToMyLocation()
        mapview?.removePOIItem(marker)
        CreatePin()
        TimerStart()

    }

    private fun CheckStop(){
        if (!allStop) {
            timer?.cancel()
            TimerStart()
        }
    }

    private fun CreatePin(){
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(
            myLocationManager.latitude,
            myLocationManager.longitude
        )
        mapview?.addPOIItem(marker)
    }

    private fun TimerStart() {

        timer = timer(period = 3000, initialDelay = 3000) {
            GoToMyLocation()
            mapview?.removePOIItem(marker)
            CreatePin()
            for (i in mapview?.poiItems!!){
                Log.e("HEYing","${i.tag}")
            }
                //Log.e("Map", "${myLocationManager.latitude} ${myLocationManager.longitude}")
        }


    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Tuched")
        timer?.cancel()
        StopTracking()
        marker.mapPoint=p1
        p0?.removePOIItem(marker)
        p0?.addPOIItem(marker)
        GoToMyLocation(marker.mapPoint)
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Double_Tapped")
        CheckStop()

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Drag_Start")
        CheckStop()

    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Drag End")
        CheckStop()
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Map_Move_Finished")
        CheckStop()
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Center_Moved")
        CheckStop()
    }


    override fun onMapViewInitialized(p0: MapView?) {
        Log.e("Map", "Initialized")
        mapview=p0
        StartTracking()

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        CheckStop()
        Log.e("Map","LongPressed")
    }


    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        CheckStop()
    }
}