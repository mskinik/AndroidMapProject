package com.mustafasuleymankinik.deneme123.directions

import com.google.gson.annotations.SerializedName

data class RoutesItem(@SerializedName("summary")
                      val summary: String? = "",
                      @SerializedName("copyrights")
                      val copyrights: String? = "",
                      @SerializedName("legs")
                      val legs: List<LegsItem>,
                      @SerializedName("overview_polyline")
                      val overviewPolyline: OverviewPolyline)