package com.mustafasuleymankinik.androidmapproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import retrofit2.http.GET


@Dao
interface MapsDao {
    @Query("SELECT * FROM routeTable")
    fun getDb():List<RouteDetail>
    @Insert
    fun postDb(routeDetail: RouteDetail)
}