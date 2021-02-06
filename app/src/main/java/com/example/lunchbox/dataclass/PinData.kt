package com.example.lunchbox.dataclass

import net.daum.mf.map.api.MapPOIItem

data class PinData(
    var pin:MapPOIItem,
    val place: Place?
)