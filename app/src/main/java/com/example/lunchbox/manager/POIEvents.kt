package com.example.lunchbox.manager

import android.util.Log
import com.example.lunchbox.activity.MainActivity
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*
import kotlin.concurrent.timer


class POIEvents(val circle: MapCircle,val rangePoint: MapPOIItem):MapView.POIItemEventListener {
    var rangeAppointed=false
    var DistanceManager= DistanceManager()
    var PinPoint:MapPOIItem?=null
    var thread: Timer?=null//Thread?=null
    var PrevLocationX=0.0
    var PrevLocationY=0.0
    fun CirclePainter(p0:MapView?,p1: MapPOIItem?){
        Log.e("P1 is","${p1?.itemName}")
        //p1= rangePoint, p0= MapView


        thread= timer(period = 100){
            if(PrevLocationX!=p1?.mapPoint?.mapPointScreenLocation?.x || PrevLocationY!=p1?.mapPoint?.mapPointScreenLocation?.y)
            {
                //전 위치 갱신.
                PrevLocationX=p1?.mapPoint?.mapPointScreenLocation!!.x
                PrevLocationY=p1?.mapPoint?.mapPointScreenLocation!!.y


                //전 Circle 지우기
                p0?.removeCircle(circle)



                val rangePointX=p1?.mapPoint?.mapPointScreenLocation!!.x
                val rangePointY = p1?.mapPoint?.mapPointScreenLocation!!.y
                val locationPointX=p1?.mapPoint?.mapPointScreenLocation!!.x
                val locationPointY=p1?.mapPoint?.mapPointScreenLocation!!.y

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
                Log.e("HEYYYYYY","Doneeeeeee")
            }


        }

    }
    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {}

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        //범위 지정 오브젝트 일때만 실행.
        Log.e("Moved","Finished")
        p0?.removeCircle(circle)
        thread?.cancel()

    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        if(p1?.tag== MainActivity.POIItemNumber.PICKMARKER.number){

            PinPoint=p1
            rangePoint.mapPoint= MapPoint.mapPointWithScreenLocation(
                p1?.mapPoint.mapPointScreenLocation.x+10.0,
                p1?.mapPoint.mapPointScreenLocation.y
            )

            Log.e("POIItem","PickMarker Selected")

            //rangePoint 추가.
            p0?.addPOIItem(rangePoint)

        }

        //RangePoint 클릭하면 로그.
        if (p1?.tag== MainActivity.POIItemNumber.RANGEPOINT.number){
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