package com.mustafasuleymankinik.androidmapproject.network

import com.mustafasuleymankinik.androidmapproject.model.Locations
import com.mustafasuleymankinik.androidmapproject.model.PostValues
import io.reactivex.Single

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkInterface {
    @GET("case-study/5/stations")
    fun getLocations(): Single<List<Locations>>
    @POST("case-study/5/stations/{id}")
    fun postLocations(@Path("id") id:String, @Body postValues: PostValues):Single<Unit>

    @GET("case-study/5/stations")
    fun getLocationss(): Call<List<Locations>>

}