package com.example.lunchbox

import android.graphics.Color
import android.util.Log
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class POIEvents(var circle: MapCircle,var rangePoint: MapPOIItem):MapView.POIItemEventListener {
    var rangeAppointed=false
    var DistanceManager=DistanceManager()


    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {}

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        if (p1?.tag==MainActivity.POIItemNumber.RANGEPOINT.number){
            p0?.removeCircle(circle)


            val rangePointX=p2?.mapPointScreenLocation!!.x
            val rangePointY = p2?.mapPointScreenLocation!!.y
            val locationPointX=p1.mapPoint.mapPointScreenLocation!!.x
            val locationPointY=p1.mapPoint.mapPointScreenLocation!!.y


            val radius=DistanceManager.Pythagoras(
                rangePointX,
                rangePointY,
                locationPointX,
                locationPointY
            )
            circle.center=p0?.mapCenterPoint
            circle.radius=radius.toInt()
            circle.fillColor= Color.argb(128,200,200,0)
            p0?.addCircle(circle)
            Log.e("Circle","${circle.radius}")
            Log.e("Guys","%x".format(circle.fillColor))
        }
        else{

        }
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if(p1?.tag==MainActivity.POIItemNumber.PICKMARKER.number){
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