package com.mustafasuleymankinik.androidmapproject.presenter

import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mustafasuleymankinik.androidmapproject.R
import com.mustafasuleymankinik.androidmapproject.contract.MapsActivityContract
import com.mustafasuleymankinik.androidmapproject.model.Locations
import com.mustafasuleymankinik.androidmapproject.network.NetworkClient
import com.mustafasuleymankinik.androidmapproject.network.NetworkInterface
import com.mustafasuleymankinik.androidmapproject.network.URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivityPresenter(context: Context, googleMap: GoogleMap):MapsActivityContract.Presenter{
    var vContext:Context=context
    var vMap=googleMap
    lateinit var busIcon: BitmapDescriptor
    lateinit var workLatLng: LatLng
    lateinit var workPlaceIcon: BitmapDescriptor
    lateinit var currentIcon:BitmapDescriptor
    lateinit var vView: MapsActivityContract.View
    override fun setView(view: MapsActivityContract.View) {
        vView=view
        vView.initView()
        vView.permission()
    }

    override fun getLocation() {

        NetworkClient.clientService(URL).getLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                response->
                println("bak bura: $response")

                for(location:Locations in response)
                addMarker(location)
            }


        vView.clicks(workLatLng)
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

    override fun drawRoute(startLatLng: LatLng,workLatLng: LatLng) {


    }


    override fun addMarker(l:Locations) {
        val stringLatLng=l.centerCoordinates?.split(",")!!
        if(l.id=="ERR-400")
        {
            workLatLng= LatLng(stringLatLng.get(0).toDouble(),stringLatLng.get(1).toDouble())
            vMap.addMarker(MarkerOptions().position(LatLng(stringLatLng.get(0).toDouble(),stringLatLng.get(1).toDouble())).icon(workPlaceIcon).title(l.name))
        }
        else
        {
            vMap.addMarker(MarkerOptions().position(LatLng(stringLatLng.get(0).toDouble(),stringLatLng.get(1).toDouble())).icon(busIcon).title(l.name))
        }

    }
}