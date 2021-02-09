package com.example.lunchbox.event

import net.daum.mf.map.api.MapReverseGeoCoder

class MapReverseLocation(private val doing:(address:String)->Unit):MapReverseGeoCoder.ReverseGeoCodingResultListener {

    
    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {
        p1?.let{doing(it)}

    }

    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
        doing("Not Found")
    }

}