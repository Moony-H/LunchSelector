package com.example.lunchbox

import android.app.Activity
import android.util.Log
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*
import kotlin.concurrent.timer



class MapViewEvents(val ActivityToUse:Activity,val myLocationManager: LocationManagement,val marker:MapPOIItem): MapView.MapViewEventListener {
    var timer: Timer? = null
    var allStop = false
    var mapview:MapView?=null




    fun StopTracking(){allStop=true}

    fun StartTracking(){
        allStop=false
        ActivityToUse.runOnUiThread {
            mapview?.setMapCenterPoint(
                MapPoint.mapPointWithGeoCoord(
                    myLocationManager.latitude,
                    myLocationManager.longitude
                ), true)

        }
    }

    private fun CheckStop(){
        if (!allStop) {
            timer?.cancel()
            TimerStart()
        }
    }

    private fun TimerStart() {

        timer = timer(period = 3000, initialDelay = 3000) {
            ActivityToUse.runOnUiThread {
                mapview?.setMapCenterPoint(
                    MapPoint.mapPointWithGeoCoord(
                        myLocationManager.latitude,
                        myLocationManager.longitude
                    ), true)

                mapview?.removePOIItem(marker)
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(
                    myLocationManager.latitude,
                    myLocationManager.longitude
                )
                mapview?.addPOIItem(marker)
                //Log.e("Map", "${myLocationManager.latitude} ${myLocationManager.longitude}")
            }

        }
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        Log.e("Map", "Tuched")
        CheckStop()
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
        TimerStart()
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        timer?.cancel()
        allStop = true
        mapview?.addPOIItem(marker)
    }


    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        CheckStop()
    }
}