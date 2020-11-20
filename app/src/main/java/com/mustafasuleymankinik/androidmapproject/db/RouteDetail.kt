package com.mustafasuleymankinik.androidmapproject.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routeTable")
data class RouteDetail (
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    @ColumnInfo(name = "start_id")
    var startId:String?=null,
    @ColumnInfo(name = "end_id")
    var endId:String?=null,
    @ColumnInfo(name = "distance")
    var distance:Int?=null
)