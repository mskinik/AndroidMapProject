package com.mustafasuleymankinik.deneme123.directions

import com.google.gson.annotations.SerializedName

data class Directions(@SerializedName("routes")
                      val routes: List<RoutesItem>,
                      @SerializedName("status")
                      val status: String = "")