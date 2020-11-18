package com.mustafasuleymankinik.androidmapproject.network

import com.mustafasuleymankinik.androidmapproject.model.Locations
import com.mustafasuleymankinik.androidmapproject.model.PostValues
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
const val URL="https://demo.voltlines.com/"
const val GOOGLE_URL="https://maps.googleapis.com/maps/api/"
object NetworkClient {

    fun clientService(url:String):NetworkInterface{
        val client= Retrofit.Builder()
            .baseUrl(url)
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return client.create(NetworkInterface::class.java)
    }
}