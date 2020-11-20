package com.mustafasuleymankinik.androidmapproject.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RouteDetail::class),version = 1)
abstract class MapsActivityDatabase:RoomDatabase() {

    abstract fun mapsDao():MapsDao
    companion object {
        const val DATABASE_NAME = "maps.db"
    }


}