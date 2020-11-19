package com.mustafasuleymankinik.androidmapproject.contract

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.mustafasuleymankinik.androidmapproject.model.Locations

interface MapsActivityContract {

    interface View{
        fun clicks(workLatLng: LatLng)
        fun initView()
        fun permission()

    }
    interface Presenter{
        fun setView(view:View,context: Context,googleMap: GoogleMap)
        fun dialogs(context: Context)
        fun getLocation()
        fun customDrawables()
        fun currentPosition(po: Location)
        fun drawRoute(marker: Marker, workLatLng: LatLng)



    }
}