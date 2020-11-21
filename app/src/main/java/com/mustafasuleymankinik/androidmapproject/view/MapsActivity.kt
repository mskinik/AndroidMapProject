package com.mustafasuleymankinik.androidmapproject.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mustafasuleymankinik.androidmapproject.DaggerActivityComponent


import com.mustafasuleymankinik.androidmapproject.R
import com.mustafasuleymankinik.androidmapproject.contract.MapsActivityContract
import com.mustafasuleymankinik.androidmapproject.module.ActivityModule
import com.mustafasuleymankinik.androidmapproject.presenter.MapsActivityPresenter
import kotlinx.android.synthetic.main.activity_maps.*
import javax.inject.Inject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,MapsActivityContract.View {

    private lateinit var mMap: GoogleMap


    @Inject
    lateinit var mapsPresenter:MapsActivityPresenter
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var currentLocation:LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        DaggerActivityComponent.builder()
        .activityModule(ActivityModule(this)).build().inject(this)


    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mapsPresenter.customDrawables()
        mapsPresenter.setView(this,this,mMap)
        mapsPresenter.dialogs(this)
        mapsPresenter.getLocation()

    }

    override fun clicks(workLatLng: LatLng) {
        fabList.setOnClickListener {
            startActivity(Intent(this@MapsActivity,RouteListActivity::class.java))
        }
        mMap.setOnMarkerClickListener {
                marker->
            mapsPresenter.drawRoute(marker,workLatLng)
            fabRefresh.show()
            fabRefresh.setOnClickListener {
                mMap.clear()
                fabRefresh.hide()
                mapsPresenter.getLocation()

            }
            true
        }

    }

    override fun initView() {
        fabRefresh.hide()
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener=object:LocationListener{
            override fun onLocationChanged(p0: Location) {
                mapsPresenter.currentPosition(p0)
            }
        }
    }

    override fun permission() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,15F,locationListener)
            val lastLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if(lastLocation!=null)
            {
                mapsPresenter.currentPosition(lastLocation)
            }

        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode==1 ){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,15F,locationListener)
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}