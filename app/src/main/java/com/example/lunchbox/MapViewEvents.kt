package com.example.lunchbox

import android.app.Activity
import android.util.Log
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*
import kotlin.concurrent.timer

//val ActivityforUse:Activity
//val myLocationManagement: LocationManagement
//val mapview: MapView,val marker: MapPOIItem
class MapViewEvents(val ActivityToUse:Activity,val mapview:MapView,val myLocationManager: LocationManagement,val marker:MapPOIItem): MapView.MapViewEventListener{
    var timer: Timer?=null
    var allStop=false




    private fun TimerStart(){

        timer= timer(period = 3000,initialDelay = 3000){
            ActivityToUse.runOnUiThread{
                mapview?.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(myLocationManager.latitude,myLocationManager.longitude),true)
                mapview?.removePOIItem(marker)
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(myLocationManager.latitude,myLocationManager.longitude)
                mapview?.addPOIItem(marker)
                Log.e("Map","${myLocationManager.latitude} ${myLocationManager.longitude}")
            }

        }
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Tuched")
        timer?.cancel()
        allStop=true
    }
    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Double_Tapped")
        if(!allStop){
            timer?.cancel()
            TimerStart()
        }

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Drag_Start")
        if(!allStop){
            timer?.cancel()
            TimerStart()
        }

    }
    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Drag End")
        if(!allStop){
            timer?.cancel()
            TimerStart()
        }
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Map_Move_Finished")
        if(!allStop){
            timer?.cancel()
            TimerStart()
        }
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Center_Moved")
        if(!allStop){
            timer?.cancel()
            TimerStart()
        }
    }



    override fun onMapViewInitialized(p0: MapView?) {
        Log.e("Map","Initialized")
        TimerStart()
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
        if(!allStop){
            timer?.cancel()
            TimerStart()
        }
    }



    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        if(!allStop){
            timer?.cancel()
            TimerStart()
        }
    }
//    private fun TrackingStart(){
//        timerTask= timer(period = 3000) {
//            longitude=myLocationManager!!.longitude
//            latitude=myLocationManager!!.latitude
//            runOnUiThread{
//                mapview?.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude,longitude),true)
//                if(locationChanged()){
//                    mapview?.removePOIItem(marker)
//                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude,longitude)
//                    mapview?.addPOIItem(marker)
//                }
//
//            }
//        }
//
//
//    }
}