package com.mustafasuleymankinik.androidmapproject.network

import com.mustafasuleymankinik.androidmapproject.model.Locations
import com.mustafasuleymankinik.androidmapproject.model.PostValues
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkInterface {
    @GET("case-study/5/stations")
    suspend fun getLocations(): Response<List<Locations>>
    @POST("case-study/5/stations/{id}")
    suspend fun potLocations(@Path("id") id:String, @Body postValues: PostValues)

}