package com.mustafasuleymankinik.androidmapproject

import com.mustafasuleymankinik.androidmapproject.module.ActivityModule
import com.mustafasuleymankinik.androidmapproject.view.MapsActivity
import com.mustafasuleymankinik.androidmapproject.view.RouteListActivity
import dagger.Component

@Component(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(mapsActivity: MapsActivity)

    fun inject(routeListActivity: RouteListActivity)
}