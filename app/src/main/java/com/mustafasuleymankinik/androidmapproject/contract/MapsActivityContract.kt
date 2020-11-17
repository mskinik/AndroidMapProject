package com.mustafasuleymankinik.androidmapproject.contract

import android.location.Location

interface MapsActivityContract {

    interface View{
        fun clicks()
        fun initView()
        fun permission()

    }
    interface Presenter{
        fun setView(view:View)
        fun getLocation()
        fun customDrawables()
        fun currentPosition(po: Location)
        fun drawRoute()
    }
}