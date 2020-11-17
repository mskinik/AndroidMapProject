package com.mustafasuleymankinik.androidmapproject.network

import com.mustafasuleymankinik.androidmapproject.model.Locations
import com.mustafasuleymankinik.androidmapproject.model.PostValues
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient(url:String) {
    //private val BASE_URL="https://demo.voltlines.com/case-study/"
    val client= Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NetworkInterface::class.java)

}