package com.mustafasuleymankinik.androidmapproject.presenter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import com.mustafasuleymankinik.androidmapproject.R
import com.mustafasuleymankinik.androidmapproject.contract.MapsActivityContract
import com.mustafasuleymankinik.androidmapproject.model.Locations
import com.mustafasuleymankinik.androidmapproject.model.PostValues
import com.mustafasuleymankinik.androidmapproject.model.Route
import com.mustafasuleymankinik.androidmapproject.network.GOOGLE_URL
import com.mustafasuleymankinik.androidmapproject.network.NetworkClient
import com.mustafasuleymankinik.androidmapproject.network.URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fail_dialog.*
import kotlinx.android.synthetic.main.question_dialog.*
import kotlinx.android.synthetic.main.success_dialog.*
import javax.inject.Inject

class MapsActivityPresenter  @Inject constructor():MapsActivityContract.Presenter{
    lateinit var vContext:Context
    lateinit var vMap:GoogleMap
    lateinit var vView: MapsActivityContract.View
    lateinit var busIcon: BitmapDescriptor
    lateinit var workLatLng: LatLng
    lateinit var workPlaceIcon: BitmapDescriptor
    lateinit var currentIcon:BitmapDescriptor
    lateinit var startRouteIcon:BitmapDescriptor
    lateinit var endRouteIcon:BitmapDescriptor
    lateinit var successDialog: AlertDialog
    lateinit var failDialog: AlertDialog
    lateinit var viewSub: View
    lateinit var route: Route
    lateinit var view: View
    lateinit var suggestDialog: Dialog

    var pointListString=ArrayList<String>()
    var currentMark: Marker? =null

    override fun setView(view: MapsActivityContract.View,context: Context,googleMap: GoogleMap) {
        vContext=context
        vView=view
        vMap=googleMap
        vView=view
        vView.initView()
        vView.permission()
    }

    override fun dialogs(context: Context) {

        view=LayoutInflater.from(context).inflate(R.layout.question_dialog,null)
        viewSub=LayoutInflater.from(context).inflate(R.layout.question_dialog,null)
        failDialog=
            AlertDialog.Builder(context,R.style.DialogTheme).setView(view).setCancelable(false).create()
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

    override fun drawRoute(marker: Marker,workLatLng: LatLng) {
        suggestDialog.show()
        viewSub=LayoutInflater.from(vContext).inflate(R.layout.question_dialog,null)
        var textView2:TextView=suggestDialog.findViewById(R.id.suggestTitle)
        textView2.text=marker.title

        suggestDialog.suggestYes.setOnClickListener {
            if(marker.position==workLatLng)
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
                val networkRoute=NetworkClient.clientService(GOOGLE_URL)
                    .getDirections("${marker.position.latitude},${marker.position.longitude}","${workLatLng.latitude},${workLatLng.longitude}","")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { response->
                        if(response.status.equals("OK"))
                        {
                            val legs = response.routes[0].legs[0]
                            route = com.mustafasuleymankinik.androidmapproject.model.Route(

                                legs.startLocation.lat,
                                legs.startLocation.lng,
                                legs.endLocation.lat,
                                legs.endLocation.lng,
                                response.routes[0].overviewPolyline.points
                            )
                            val startLatLng = LatLng(route.startLat!!, route.startLng!!)
                            val endLatLng = LatLng(route.endLat!!, route.endLng!!)
                            vMap.clear()
                            vMap.addMarker( MarkerOptions().position(startLatLng).icon(startRouteIcon))
                            vMap.addMarker(MarkerOptions().position(endLatLng).icon(endRouteIcon))
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

                            sendLocation(pointListString,startLatLng,endLatLng,marker.snippet)
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
            }



    }


    private fun addMarker(l:Locations) {

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