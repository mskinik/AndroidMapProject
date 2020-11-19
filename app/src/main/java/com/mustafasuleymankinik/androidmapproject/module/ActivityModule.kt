package com.mustafasuleymankinik.androidmapproject.module

import android.app.Activity
import com.mustafasuleymankinik.androidmapproject.contract.MapsActivityContract
import com.mustafasuleymankinik.androidmapproject.presenter.MapsActivityPresenter
import com.mustafasuleymankinik.androidmapproject.view.MapsActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(activity: Activity) {

    @Provides
    fun providePresenter():MapsActivityContract.Presenter{
        return MapsActivityPresenter()
    }
}