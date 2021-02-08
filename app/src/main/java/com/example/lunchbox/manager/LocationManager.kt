package com.example.lunchbox.manager

import android.Manifest;
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;

import android.location.Location

import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.Toast
import com.example.lunchbox.R
import java.lang.NullPointerException



class LocationManager (val ActivityToUse: Activity){

    var latitude:Double=37.592128000000002//위도
    var longitude:Double=126.97942//경도


    var myLocationManager:LocationManager=ActivityToUse.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var isGPSEnabled=false
    var isNetworkEnabled=false
    //익명 클래스로 넣어줌.
    private val locationListener= object :LocationListener{
        //위치가 바뀔때 콜백할 메서드. 나는 클래스 필드에다가 넣는 것으로 마무리 했다.(더 좋은 방법이 있을 것 같다.)
        override fun onLocationChanged(location:Location){
            longitude=location.longitude
            latitude=location.latitude

        }

        override fun onProviderDisabled(provider: String) { super.onProviderDisabled(provider) }//제공자가 멈췄을 때
        override fun onProviderEnabled(provider: String) { super.onProviderEnabled(provider) }//될때
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { super.onStatusChanged(provider, status, extras) }
    }


    //내가 정의한 퍼미션 체크
    fun checkLocationPermission():Boolean{
        if (ActivityCompat.checkSelfPermission(
                ActivityToUse,
                Manifest.permission.ACCESS_FINE_LOCATION //위치 권한이 GRANTED(설정 안되어 있을 때.)
            ) != PackageManager.PERMISSION_GRANTED //&& ActivityCompat.checkSelfPermission(
                //ActivityToUse,
                //Manifest.permission.ACCESS_COARSE_LOCATION
            //) != PackageManager.PERMISSION_GRANTED
        ){

            ActivityCompat.requestPermissions(ActivityToUse, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),101)//권한 요청
            //ActivityCompat.requestPermissions(ActivityToUse, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),101)
            return false// 안되어 있었다를 확인할 수 있게 리턴 해줬다.
        }
        else{
            return true// 되어 있었다.
        }

    }



   @SuppressLint("MissingPermission")// 위에서 체크 할거라 이거 써줌.
   fun locationListenerSetting()
   {

       //checkLocationPermission()// 위의 퍼미션 체크 함수.
       //isGPSEnabled= myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)// 퍼미션은 되었지만 안켰을 수도 있으니...
       isNetworkEnabled= myLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)//마찬가지.


        // When 문을 몰랐을 때라 이렇게 썻지만 나중에 좀더 좋은 방향으로 수정할 수 있을 것 같다.
       //if(isGPSEnabled){
       //    Log.e("GPS","Enabled")
       //    val location= myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
       //    if(location==null)// 혹시나 한번더.(let 쓰면 되는데...)
       //    {
       //        Toast.makeText(ActivityToUse, R.string.GPS_Disabled_Message, Toast.LENGTH_SHORT).show()
       //    }
       //    else {
       //        longitude = location.longitude// 다 갠차느면 로케이션 업데이트.
       //        latitude = location.latitude
//
       //    }
       //}
       if(isNetworkEnabled){
           Log.e("Net","Enabled")
           val location=myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
           if(location==null)
           {
               Toast.makeText(ActivityToUse, R.string.GPS_Disabled_Message, Toast.LENGTH_SHORT).show()
           }
           else {
               longitude = location.longitude
               latitude = location.latitude
           }
       }
       else{
           Toast.makeText(ActivityToUse, R.string.GPS_Disabled_Message, Toast.LENGTH_SHORT).show()
           longitude=126.97942
           latitude=37.592128000000002
       }









   }

    @SuppressLint("MissingPermission")
    fun locationUpdateSetting(msec:Long, meter: Float) {
        //checkLocationPermission()
        //isGPSEnabled= myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled= myLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        //if(isGPSEnabled){
        //    myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,msec,meter,locationListener)
        //}
        if(isNetworkEnabled){
            myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,msec,meter,locationListener)
        }
        else{
            Toast.makeText(ActivityToUse, R.string.GPS_Disabled_Message, Toast.LENGTH_SHORT).show()
        }

    }





    fun StopLocationListener() {
        myLocationManager.removeUpdates(locationListener)
    }

}