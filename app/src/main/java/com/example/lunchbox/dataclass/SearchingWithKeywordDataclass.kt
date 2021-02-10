package com.example.lunchbox.dataclass

import java.io.Serializable


data class SearchingWithKeywordDataclass(
    val meta: KeywordMeta,
    val documents:List<Place>)

data class KeywordMeta(
    val pageable_count:Int,
    val is_end:Boolean)

data class Place(
    val place_name:String,
    val category_name:String,
    val phone:String,
    val address_name:String,
    val road_address_name:String,
    val x:Double,//longitude
    val y:Double):Serializable//latitude


