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


interface  LocationInter{

    fun CheckLocationPermission():Boolean
    fun LocationListenerSetting()
    fun LocationUpdateSetting(msec:Long, meter: Float)
    fun StopLocationListener()


}

class LocationManagement (val ActivityToUse: Activity):LocationInter{








    var latitude:Double=37.592128000000002//위도
    var longitude:Double=126.97942//경도


    var myLocationManager:LocationManager=ActivityToUse.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var isGPSEnabled=false
    var isNetworkEnabled=false
    val locationListener= object :LocationListener{
        override fun onLocationChanged(location:Location){
            longitude=location.longitude
            latitude=location.latitude
        }

        override fun onProviderDisabled(provider: String) { super.onProviderDisabled(provider) }
        override fun onProviderEnabled(provider: String) { super.onProviderEnabled(provider) }
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { super.onStatusChanged(provider, status, extras) }
    }



    override fun CheckLocationPermission():Boolean{
        if (ActivityCompat.checkSelfPermission(
                ActivityToUse,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                ActivityToUse,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){

            ActivityCompat.requestPermissions(ActivityToUse, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),101)
            ActivityCompat.requestPermissions(ActivityToUse, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),101)
            return false
        }
        else{
            return true
        }

    }



   @SuppressLint("MissingPermission")
   override fun LocationListenerSetting()
   {

       CheckLocationPermission()
       isGPSEnabled= myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
       isNetworkEnabled= myLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)



       if(isGPSEnabled){

           val location= myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
           if(location==null)
           {
               Toast.makeText(ActivityToUse, R.string.GPS_Disabled_Message, Toast.LENGTH_SHORT).show()
           }
           else {
               longitude = location?.longitude
               latitude = location?.latitude
           }
       }
       else if(isNetworkEnabled){

           val location=myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
           if(location==null)
           {
               Toast.makeText(ActivityToUse, R.string.GPS_Disabled_Message, Toast.LENGTH_SHORT).show()
           }
           else {
               longitude = location?.longitude
               latitude = location?.latitude
           }
       }
       else{
           Toast.makeText(ActivityToUse, R.string.GPS_Disabled_Message, Toast.LENGTH_SHORT).show()
           longitude=126.97942
           latitude=37.592128000000002
       }









   }

    @SuppressLint("MissingPermission")
    override fun LocationUpdateSetting(msec:Long, meter: Float) {
        CheckLocationPermission()
        isGPSEnabled= myLocationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled= myLocationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if(isGPSEnabled){
            myLocationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER,msec,meter,locationListener)
        }
        else if(isNetworkEnabled){
            myLocationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,msec,meter,locationListener)
        }
        else{
            Toast.makeText(ActivityToUse, R.string.GPS_Disabled_Message, Toast.LENGTH_SHORT).show()
        }

    }





    override fun StopLocationListener() {
        myLocationManager?.removeUpdates(locationListener)
    }

}