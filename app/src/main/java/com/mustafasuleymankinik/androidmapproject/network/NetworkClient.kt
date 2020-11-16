package com.mustafasuleymankinik.androidmapproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    private val BASE_URL="https://demo.voltlines.com/case-study/"
    val client= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NetworkInterface::class.java)

}