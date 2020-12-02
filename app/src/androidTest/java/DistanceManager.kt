import android.location.Location
import kotlin.math.PI
import kotlin.math.absoluteValue

class DistanceManager {
    fun LocationToDistance(Location1:Location, Location2:Location):Double{
        val lon1=Location1.longitude
        val lon2=Location2.longitude
        val lat1=Location1.latitude
        val lat2=Location2.latitude
        var theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))

        dist = Math.acos(dist)
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