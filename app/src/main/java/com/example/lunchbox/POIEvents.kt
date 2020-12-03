package com.example.lunchbox

import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class POIEvents(var axisPoint: MainActivity.AxisPoint):MapView.POIItemEventListener {

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?, p2: MapPOIItem.CalloutBalloonButtonType?) {}

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        if (p1==MainActivity.POIItemNumber.RANGEPOINT){
            axisPoint.xAxis=p2?.mapPointScreenLocation!!.x
            axisPoint.yAxis= p2?.mapPointScreenLocation!!.y
        }
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if(p1?.tag==MainActivity.POIItemNumber.PICKMARKER.number){
            val rangePoint=MapPOIItem()
            rangePoint.isDraggable=true
            rangePoint.markerType=MapPOIItem.MarkerType.CustomImage
            rangePoint.customImageResourceId=R.drawable.blackcircle
            rangePoint.setCustomImageAnchor(0.5f,0.5f)
            p0?.addPOIItem(rangePoint)
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