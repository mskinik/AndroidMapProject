package com.mustafasuleymankinik.androidmapproject.contract

import com.mustafasuleymankinik.androidmapproject.db.RouteDetail

interface RouteListActivityContract {
    interface View{
        fun setInit(listRoute:List<RouteDetail>)
    }
    interface Presenter{
        fun setView(view:View)
        fun dbInit()
    }
}