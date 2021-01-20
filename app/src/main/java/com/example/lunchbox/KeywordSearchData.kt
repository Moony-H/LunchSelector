package com.example.lunchbox

data class KeywordSearchData(
    var meta: KeywordMeta,
    var documents:List<place>
)

data class KeywordMeta(
    var pageable_count:Int,
    var is_end:Boolean
)

data class place(
    var place_name:String,
    var category_name:String,
    var phone:String,
    var address_name:String,
    var road_address_name:String,
    var x:Double,
    var y:Double
)