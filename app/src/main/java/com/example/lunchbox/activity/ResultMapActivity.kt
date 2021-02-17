package com.example.lunchbox.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lunchbox.R
import com.example.lunchbox.dataclass.Place
import com.example.lunchbox.staticMethod.DistanceManager
import com.example.lunchbox.staticMethod.StaticUtils
import com.example.lunchbox.tags.POIItemTag
import kotlinx.android.synthetic.main.activity_result_map.*
import net.daum.mf.map.api.*
import kotlin.math.roundToInt

class ResultMapActivity:AppCompatActivity(){

    private var userLongitude=0.0
    private var userLatitude=0.0
    private var placeLongitude=0.0
    private var placeLatitude=0.0
    val onClickListener= View.OnClickListener {
        val packageName="kakaomap://route?sp=${userLatitude},${userLongitude}&ep=${placeLatitude},$placeLongitude{}&by="
        try {
            when(it.id){
                result_map_drive_button.id -> {
                    val intent = packageManager.getLaunchIntentForPackage(packageName + "CAR")
                    startActivity(intent)
                }
                result_map_public_transport_button.id -> {
                    val intent =
                        packageManager.getLaunchIntentForPackage(packageName + "PUBLICTRANSIT")
                    startActivity(intent)

                }
                result_map_walk_button.id -> {
                    val intent = packageManager.getLaunchIntentForPackage(packageName + "FOOT")
                    startActivity(intent)

                }

            }
        }catch (e: Exception){
            Toast.makeText(this,"카카오맵이 없습니다. 설치 페이지로 넘어 갑니다.",Toast.LENGTH_SHORT).show()
            val url = "market://details?id=net.daum.android.map"
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

    }





    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_map)
        StaticUtils.setFullScreenMode(this)
        Log.e("LifeCycle", "onCreate")
        //인텐트 받아온거 세팅
        intent.extras?.let {
            userLongitude=it.getDouble("UserLongitude")
            userLatitude=it.getDouble("UserLatitude")

        }

        //리스너 세팅
        result_map_drive_button.setOnClickListener(onClickListener)
        result_map_public_transport_button.setOnClickListener(onClickListener)
        result_map_walk_button.setOnClickListener(onClickListener)



        val resultPlace= intent.getSerializableExtra("Place") as Place
        placeLongitude=resultPlace.x
        placeLatitude=resultPlace.y
        val mapView=MapView(this)
        val viewGroup = result_map_layout
        viewGroup.addView(mapView)  //맵을 뷰에 추가


        val placeMarker= MapPOIItem()//공통 마커 생성

        placeMarker.itemName="placeMarker"
        placeMarker.mapPoint = MapPoint.mapPointWithGeoCoord(placeLatitude, placeLongitude)
        placeMarker.markerType= MapPOIItem.MarkerType.BluePin
        placeMarker.selectedMarkerType= MapPOIItem.MarkerType.RedPin
        placeMarker.tag= POIItemTag.PIN

        val userMarker=MapPOIItem()

        userMarker.itemName="userMarker"
        userMarker.mapPoint = MapPoint.mapPointWithGeoCoord(userLatitude, userLongitude)
        userMarker.markerType= MapPOIItem.MarkerType.BluePin
        userMarker.selectedMarkerType= MapPOIItem.MarkerType.RedPin
        userMarker.tag= POIItemTag.PIN

        mapView.addPOIItem(placeMarker)
        mapView.addPOIItem(userMarker)


        Log.e("placeMarker", "Long: $placeLongitude Lati:$placeLatitude")
        Log.e("userMarker", "Long: $userLongitude Lati:$userLatitude")

        val polyline=MapPolyline()
        polyline.tag=POIItemTag.LINE
        polyline.lineColor= Color.argb(128, 255, 51, 0)
        polyline.addPoint(userMarker.mapPoint)
        polyline.addPoint(placeMarker.mapPoint)
        mapView.addPolyline(polyline)

        val distance= ((DistanceManager.locationToDistance(
            userMarker.mapPoint,
            placeMarker.mapPoint
        )) * 10).roundToInt() /10f

        result_map_distance.text="${distance}M"

        val mapPointBounds=MapPointBounds(polyline.mapPoints)
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, 100))

    }
}