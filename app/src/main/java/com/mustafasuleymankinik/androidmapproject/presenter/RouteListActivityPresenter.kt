package com.mustafasuleymankinik.androidmapproject.presenter

import com.mustafasuleymankinik.androidmapproject.contract.RouteListActivityContract
import com.mustafasuleymankinik.androidmapproject.db.MapsActivityDatabase
import com.mustafasuleymankinik.androidmapproject.db.MapsDao
import javax.inject.Inject

class RouteListActivityPresenter @Inject constructor():RouteListActivityContract.Presenter {
    lateinit var vView:RouteListActivityContract.View
    @Inject
    lateinit var mapsActivityDatabase: MapsActivityDatabase

    override fun setView(view: RouteListActivityContract.View) {
        vView=view
    }

    override fun dbInit() {

        val listRoute=mapsActivityDatabase.mapsDao().getDb()
        vView.setInit(listRoute)
    }

}