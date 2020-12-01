package com.example.lunchbox

import android.util.Log
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapViewEvents: MapView.MapViewEventListener{
    var AnyEvent=false
    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Tuched")
    }
    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Drag_Start")

    }
    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Drag End")
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
        Log.e("Map","Map_Move_Finished")
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {

    }



    override fun onMapViewInitialized(p0: MapView?) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }



    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

    }
}