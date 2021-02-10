package com.example.lunchbox.activity

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.tags.POIItemTag
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result_map.*
import net.daum.mf.map.api.*

class ResultMapActivity:AppCompatActivity(){

    private var userLongitude=0.0
    private var userLatitude=0.0
    private var placeLongitude=0.0
    private var placeLatitude=0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_map)
        Log.e("LifeCycle","onCreate")
        //인텐트 받아온거 세팅
        intent.extras?.let {
            userLongitude=it.getDouble("UserLongitude")
            userLatitude=it.getDouble("UserLatitude")

        }
        val resultPlace= intent.getSerializableExtra("Place") as Place
        placeLongitude=resultPlace.x
        placeLatitude=resultPlace.y
        val mapView=MapView(this)
        val viewGroup = result_map_layout
        viewGroup.addView(mapView)  //맵을 뷰에 추가


        val placeMarker= MapPOIItem()//공통 마커 생성

        placeMarker.itemName="placeMarker"
        placeMarker.mapPoint = MapPoint.mapPointWithGeoCoord(placeLatitude,placeLongitude)
        placeMarker.markerType= MapPOIItem.MarkerType.BluePin
        placeMarker.selectedMarkerType= MapPOIItem.MarkerType.RedPin
        placeMarker.tag= POIItemTag.PIN

        val userMarker=MapPOIItem()

        userMarker.itemName="userMarker"
        userMarker.mapPoint = MapPoint.mapPointWithGeoCoord(userLatitude,userLongitude)
        userMarker.markerType= MapPOIItem.MarkerType.BluePin
        userMarker.selectedMarkerType= MapPOIItem.MarkerType.RedPin
        userMarker.tag= POIItemTag.PIN

        mapView.addPOIItem(placeMarker)
        mapView.addPOIItem(userMarker)


        Log.e("placeMarker","Long: $placeLongitude Lati:$placeLatitude")
        Log.e("userMarker","Long: $userLongitude Lati:$userLatitude")

        val polyline=MapPolyline()
        polyline.tag=POIItemTag.LINE
        polyline.lineColor= Color.argb(128, 255, 51, 0)
        polyline.addPoint(userMarker.mapPoint)
        polyline.addPoint(placeMarker.mapPoint)
        mapView.addPolyline(polyline)

        val mapPointBounds=MapPointBounds(polyline.mapPoints)
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds,100))

    }
}