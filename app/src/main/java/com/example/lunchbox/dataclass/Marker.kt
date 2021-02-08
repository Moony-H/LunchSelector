package com.example.lunchbox.dataclass

import net.daum.mf.map.api.MapPOIItem

data class Marker(
    var pin:MapPOIItem,
    var place: Place?
)