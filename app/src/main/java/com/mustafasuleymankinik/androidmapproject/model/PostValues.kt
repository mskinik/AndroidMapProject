package com.mustafasuleymankinik.androidmapproject.model

import com.google.gson.annotations.SerializedName

data class PostValues (
    @SerializedName("id")
    var id:String,
    @SerializedName("polyline")
    var polyline:List<String>?,
    @SerializedName("origin_coordinates")
    var origin_coordinates:String?,
    @SerializedName("destination_coordinates")
    var destination_coordinates:String?
)