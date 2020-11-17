package com.mustafasuleymankinik.androidmapproject.presenter

import android.content.Context
import android.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mustafasuleymankinik.androidmapproject.R
import com.mustafasuleymankinik.androidmapproject.contract.MapsActivityContract

class MapsActivityPresenter(context: Context, googleMap: GoogleMap):MapsActivityContract.Presenter{
    var vContext:Context=context
    var vMap=googleMap
    lateinit var busIcon: BitmapDescriptor
    lateinit var workPlaceIcon: BitmapDescriptor
    lateinit var currentIcon:BitmapDescriptor
    lateinit var vView: MapsActivityContract.View
    override fun setView(view: MapsActivityContract.View) {
        vView=view
        vView.initView()
        vView.permission()
    }

    override fun getLocation() {

        vView.clicks()
    }

    override fun customDrawables() {
        busIcon=BitmapDescriptorFactory.fromResource(R.drawable.group12)
        workPlaceIcon=BitmapDescriptorFactory.fromResource(R.drawable.nounwork)
        currentIcon=BitmapDescriptorFactory.fromResource(R.drawable.oval)

    }

    override fun currentPosition(location:Location) {
        vMap.addMarker(MarkerOptions().position(LatLng(location.latitude,location.longitude)).icon(currentIcon).title("Konumum"))
        vMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,location.longitude),15F))
    }

    override fun drawRoute() {

    }
}