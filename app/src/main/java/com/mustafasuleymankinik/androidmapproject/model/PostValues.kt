package com.mustafasuleymankinik.androidmapproject.model

import com.google.gson.annotations.SerializedName

data class PostValues (
    @SerializedName("polyline")
    var polyline:ArrayList<String>?,
    @SerializedName("origin_coordinates")
    var origin_coordinates:String?,
    @SerializedName("destination_coordinates")
    var destination_coordinates:String?
)