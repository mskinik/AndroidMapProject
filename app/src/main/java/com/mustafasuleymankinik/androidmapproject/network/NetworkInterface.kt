package com.mustafasuleymankinik.androidmapproject.network

import com.mustafasuleymankinik.androidmapproject.model.Locations
import com.mustafasuleymankinik.androidmapproject.model.PostValues
import com.mustafasuleymankinik.deneme123.directions.Directions
import io.reactivex.Single

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NetworkInterface {
    @GET("case-study/5/stations")
    fun getLocations(): Single<List<Locations>>
    @POST("case-study/5/stations/{id}")
    fun postLocations(@Path("id") id:String, @Body postValues: PostValues):Single<Unit>

    @GET("directions/json")
    fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Single<Directions>

}