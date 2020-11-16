package com.mustafasuleymankinik.androidmapproject.network

import com.mustafasuleymankinik.androidmapproject.model.Locations
import retrofit2.Response
import retrofit2.http.GET

interface NetworkInterface {
    @GET("case-study/5/stations")
    fun getLocations(): Response<List<Locations>>

}