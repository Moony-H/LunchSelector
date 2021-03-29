package com.example.lunchbox.manager

import android.location.Location
import kotlin.math.*

class DistanceManager {
    fun pythagoras(x1:Double,y1:Double,x2:Double,y2:Double):Double{
        val x=x1-x2
        val y=y2-y1
        return Math.sqrt(x*x+y*y)
    }
    fun locationToDistance(Location1:Location, Location2:Location):Double{
        val lon1=Location1.longitude
        val lon2=Location2.longitude
        val lat1=Location1.latitude
        val lat2=Location2.latitude
        var theta = lon1 - lon2
        var dist = sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(deg2rad(theta))

        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60 * 1.1515* 1609.344


        return dist
    }


    // This function converts decimal degrees to radians
    private fun deg2rad(deg:Double):Double {
        return (deg * Math.PI / 180.0)
    }

    // This function converts radians to decimal degrees
    private fun rad2deg(rad:Double):Double {
        return (rad * 180 / Math.PI)
    }

}