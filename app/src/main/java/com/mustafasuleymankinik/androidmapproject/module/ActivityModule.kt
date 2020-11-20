package com.mustafasuleymankinik.androidmapproject.module

import android.app.Activity
import android.content.Context
import androidx.room.Room
import com.mustafasuleymankinik.androidmapproject.contract.MapsActivityContract
import com.mustafasuleymankinik.androidmapproject.db.MapsActivityDatabase
import com.mustafasuleymankinik.androidmapproject.db.MapsDao
import com.mustafasuleymankinik.androidmapproject.presenter.MapsActivityPresenter
import com.mustafasuleymankinik.androidmapproject.view.MapsActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val context: Context) {
    var moduleContext:Context=context

    @Provides
    fun provideContext():Context{
        return moduleContext
    }

    @Provides
    fun providePresenter():MapsActivityContract.Presenter{
        return MapsActivityPresenter()
    }

    @Provides
    fun provideDatabase():MapsActivityDatabase{
        return Room.databaseBuilder(moduleContext, MapsActivityDatabase::class.java, MapsActivityDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideDao(mapsActivityDatabase: MapsActivityDatabase):MapsDao{
       return mapsActivityDatabase.mapsDao()
    }
}