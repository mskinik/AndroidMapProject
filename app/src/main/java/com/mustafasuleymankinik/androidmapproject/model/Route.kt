package com.mustafasuleymankinik.androidmapproject.model

import com.mustafasuleymankinik.deneme123.directions.OverviewPolyline

data class Route (
    val startLat:Double?,
    val startLng:Double?,
    val endLat: Double?,
    val endLng: Double?,
    val overviewPolyline: String=""
)