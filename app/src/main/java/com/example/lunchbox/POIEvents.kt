package com.example.lunchbox

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*
import kotlin.concurrent.timer


class POIEvents(var circle: MapCircle,var rangePoint: MapPOIItem):MapView.POIItemEventListener {
    var rangeAppointed=false
    var DistanceManager=DistanceManager()
    var PinPoint:MapPOIItem?=null
    var thread: Timer?=null//Thread?=null

    fun CirclePainter(p0:MapView?,p1: MapPOIItem?){
        thread= timer(period = 100){
            p0?.removeCircle(circle)
            Log.e("hey","RangePoint is ${rangePoint?.mapPoint?.mapPointScreenLocation!!.x} ${rangePoint?.mapPoint?.mapPointScreenLocation!!.y}")
            var rangePoint=p0?.findPOIItemByTag(MainActivity.POIItemNumber.RANGEPOINT.number)
            val rangePointX=rangePoint?.mapPoint?.mapPointScreenLocation!!.x
            val rangePointY = rangePoint?.mapPoint?.mapPointScreenLocation!!.y
            val locationPointX=PinPoint?.mapPoint?.mapPointScreenLocation!!.x
            val locationPointY=PinPoint?.mapPoint?.mapPointScreenLocation!!.y

            //피타고라스로 두개의 점의 거리 재기.
            val radius=DistanceManager.Pythagoras(
                rangePointX,
                rangePointY,
                locationPointX,
                locationPointY
            )

            //원의 가운데 점 지정.
            circle.center=PinPoint?.mapPoint
            circle.radius=radius.toInt()
            p0?.addCircle(circle)

        }
//        thread=Thread(){
//            p0?.removeCircle(circle)
//            val rangePointX=p1?.mapPoint?.mapPointScreenLocation!!.x
//            val rangePointY = p1?.mapPoint?.mapPointScreenLocation!!.y
//            val locationPointX=PinPoint?.mapPoint?.mapPointScreenLocation!!.x
//            val locationPointY=PinPoint?.mapPoint?.mapPointScreenLocation!!.y
//
//            //피타고라스로 두개의 점의 거리 재기.
//            val radius=DistanceManager.Pythagoras(
//                rangePointX,
//                rangePointY,
//                locationPointX,
//                locationPointY
//            )
//
//            //원의 가운데 점 지정.
//            circle.center=PinPoint?.mapPoint
//            circle.radius=radius.toInt()
//            p0?.addCircle(circle)}
//        thread?.start()

    }
    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {}

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        //범위 지정 오브젝트 일때만 실행.
        p0?.removeCircle(circle)
        thread?.cancel()

    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if(p1?.tag==MainActivity.POIItemNumber.PICKMARKER.number){
            PinPoint=p1
            rangePoint.mapPoint= MapPoint.mapPointWithScreenLocation(
                p1.mapPoint.mapPointScreenLocation.x+10.0,
                p1.mapPoint.mapPointScreenLocation.y
            )

            Log.e("its","${p1.mapPoint.mapPointScreenLocation.x} ${p1.mapPoint.mapPointScreenLocation.y}")

            Log.e("Pin","selected")
            p0?.addPOIItem(rangePoint)

            for (i in p0?.poiItems!!){
                Log.e("Guys","${i.itemName}")
            }
            Log.e("Guys","${rangePoint.mapPoint.mapPointScreenLocation.x} ${rangePoint.mapPoint.mapPointScreenLocation.y}")
        }
        if (p1?.tag==MainActivity.POIItemNumber.RANGEPOINT.number){
            Log.e("Guys","RangePoint is selected")
            CirclePainter(p0,p1)
        }
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        TODO("Not yet implemented")
    }

}