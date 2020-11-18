package com.mustafasuleymankinik.deneme123.directions

import com.google.gson.annotations.SerializedName

data class Distance(@SerializedName("text")
                    val text: String = "",
                    @SerializedName("value")
                    val value: Int = 0)