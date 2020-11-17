package com.mustafasuleymankinik.androidmapproject.contract

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.mustafasuleymankinik.androidmapproject.model.Locations

interface MapsActivityContract {

    interface View{
        fun clicks(workLatLng: LatLng)
        fun initView()
        fun permission()

    }
    interface Presenter{
        fun setView(view:View)
        fun getLocation()
        fun customDrawables()
        fun currentPosition(po: Location)
        fun drawRoute(startLatLng: LatLng,workLatLng: LatLng)
        fun addMarker(locations: Locations)
    }
}