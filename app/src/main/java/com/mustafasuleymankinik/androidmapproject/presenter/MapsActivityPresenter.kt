package com.mustafasuleymankinik.androidmapproject.presenter

import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.mustafasuleymankinik.androidmapproject.R
import com.mustafasuleymankinik.androidmapproject.contract.MapsActivityContract
import com.mustafasuleymankinik.androidmapproject.model.Locations
import com.mustafasuleymankinik.androidmapproject.model.PostValues
import com.mustafasuleymankinik.androidmapproject.network.GOOGLE_URL
import com.mustafasuleymankinik.androidmapproject.network.NetworkClient
import com.mustafasuleymankinik.androidmapproject.network.URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fail_dialog.*
import kotlinx.android.synthetic.main.question_dialog.*
import kotlinx.android.synthetic.main.success_dialog.*
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
    lateinit var startRouteIcon:BitmapDescriptor
    lateinit var endRouteIcon:BitmapDescriptor
    lateinit var vView: MapsActivityContract.View
    lateinit var successDialog: AlertDialog
    lateinit var failDialog: AlertDialog
    lateinit var suggestDialog: AlertDialog
    var pointListString=ArrayList<String>()
    var currentMark: Marker? =null
    override fun setView(view: MapsActivityContract.View) {
        vView=view
        vView.initView()
        vView.permission()
    }

    override fun dialogs(context: Context) {

        failDialog=
            AlertDialog.Builder(context,R.style.DialogTheme).setView(LayoutInflater.from(context).inflate(R.layout.fail_dialog,null)).setCancelable(false).create()
        successDialog=
            AlertDialog.Builder(context,R.style.DialogTheme).setView(LayoutInflater.from(context).inflate(R.layout.success_dialog,null)).setCancelable(false).create()
        suggestDialog=
            AlertDialog.Builder(context,R.style.DialogTheme).setView(LayoutInflater.from(context).inflate(R.layout.question_dialog,null)).setCancelable(false).create()
    }

    override fun getLocation() {

        NetworkClient.clientService(URL).getLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                    response->

                for(location:Locations in response)
                    addMarker(location)
            }



    }

    override fun customDrawables() {
        busIcon=BitmapDescriptorFactory.fromResource(R.drawable.group12)
        workPlaceIcon=BitmapDescriptorFactory.fromResource(R.drawable.nounwork)
        currentIcon=BitmapDescriptorFactory.fromResource(R.drawable.oval)
        startRouteIcon=BitmapDescriptorFactory.fromResource(R.drawable.ovalcopy2)
        endRouteIcon=BitmapDescriptorFactory.fromResource(R.drawable.ovalcopy)

    }

    override fun currentPosition(location:Location) {
        if(currentMark!=null)
        {
            currentMark?.remove()
        }
        currentMark=vMap.addMarker(MarkerOptions().position(LatLng(location.latitude,location.longitude)).icon(currentIcon).title("Konumum"))
        vMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,location.longitude),15F))
    }

    override fun drawRoute(startLatLng: LatLng,workLatLng: LatLng,id:String) {


        suggestDialog.show()

        suggestDialog.suggestYes.setOnClickListener {
            if(startLatLng==workLatLng)
            {
                failDialog.show()
                failDialog.failGiveUp.setOnClickListener {
                    failDialog.dismiss()
                }
                failDialog.failRetry.setOnClickListener{
                    failDialog.dismiss()
                }

            }
            else
            {
                val route=NetworkClient.clientService(GOOGLE_URL).getDirections("${startLatLng.latitude},${startLatLng.longitude}","${workLatLng.latitude},${workLatLng.longitude}","AIzaSyCSkipMTQc3FcjwNFZLz91dKhCATp1kglI")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { response->
                        if(response.status.equals("OK"))
                        {
                            val legs = response.routes[0].legs[0]
                            val route = com.mustafasuleymankinik.androidmapproject.model.Route(
                                "",
                                "",
                                legs.startLocation.lat,
                                legs.startLocation.lng,
                                legs.endLocation.lat,
                                legs.endLocation.lng,
                                response.routes[0].overviewPolyline.points
                            )
                            val startLatLng = LatLng(route.startLat!!, route.startLng!!)
                            val endLatLng = LatLng(route.endLat!!, route.endLng!!)
                            vMap.clear()
                            vMap.addMarker( MarkerOptions().position(startLatLng).icon(startRouteIcon).title(route.startName))
                            vMap.addMarker(MarkerOptions().position(endLatLng).icon(endRouteIcon).title(route.endName))
                            vMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endLatLng, 15F))
                            val polylineOptions = PolylineOptions()

                            polylineOptions.width(10F)

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                polylineOptions.color(vContext.resources.getColor(R.color.colorPrimaryDark, vContext.theme))
                            } else {
                                polylineOptions.color(vContext.resources.getColor(R.color.colorPrimaryDark))
                            }
                            val pointsList = PolyUtil.decode(route.overviewPolyline)

                            for (point in pointsList) {
                                polylineOptions.add(point)
                                pointListString.add(point.toString())

                            }

                            sendLocation(pointListString,startLatLng,endLatLng,id)
                            vMap.addPolyline(polylineOptions)

                        }

                    }
                suggestDialog.dismiss()
                successDialog.show()
                successDialog.successOK.setOnClickListener {
                    successDialog.dismiss()
                }
            }

        }
        suggestDialog.suggestNo.setOnClickListener {
            suggestDialog.dismiss()
        }





    }

    private fun sendLocation(
        pointListString: ArrayList<String>,
        startLatLng: LatLng,
        endLatLng: LatLng,
        id: String) {

        NetworkClient.clientService(URL).postLocations(id,
            PostValues(id,pointListString,startLatLng.toString(),endLatLng.toString())
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                response->
                Log.d("TAG", "response : $response ")
            }



    }


    override fun addMarker(l:Locations) {

        val stringLatLng=l.centerCoordinates?.split(",")!!
        if(l.id=="ERR-400")
        {
            workLatLng= LatLng(stringLatLng.get(0).toDouble(),stringLatLng.get(1).toDouble())
            vMap.addMarker(MarkerOptions().position(LatLng(stringLatLng.get(0).toDouble(),stringLatLng.get(1).toDouble())).icon(workPlaceIcon).snippet(l.id).title(l.name))
            vView.clicks(workLatLng)
        }
        else
        {

            vMap.addMarker(MarkerOptions().position(LatLng(stringLatLng.get(0).toDouble(),stringLatLng.get(1).toDouble())).icon(busIcon).snippet(l.id).title(l.name))
        }

    }

}