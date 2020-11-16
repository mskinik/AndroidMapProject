package com.mustafasuleymankinik.androidmapproject.model

import com.google.gson.annotations.SerializedName

data class Locations (
    @SerializedName("id")
    val id:String?,
    @SerializedName("name")
    val name:String?,
    @SerializedName("count")
    val count:Int?,
    @SerializedName("center_coordinates")
    val centerCoordinates:String?
)