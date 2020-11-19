package com.mustafasuleymankinik.androidmapproject

import com.mustafasuleymankinik.androidmapproject.module.ActivityModule
import com.mustafasuleymankinik.androidmapproject.view.MapsActivity
import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mapsActivity: MapsActivity)
}