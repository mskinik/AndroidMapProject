package com.mustafasuleymankinik.androidmapproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafasuleymankinik.androidmapproject.DaggerActivityComponent

import com.mustafasuleymankinik.androidmapproject.R
import com.mustafasuleymankinik.androidmapproject.adapter.RouteDetailAdapter
import com.mustafasuleymankinik.androidmapproject.db.MapsActivityDatabase
import com.mustafasuleymankinik.androidmapproject.db.MapsDao
import com.mustafasuleymankinik.androidmapproject.module.ActivityModule
import kotlinx.android.synthetic.main.activity_route_list.*
import javax.inject.Inject

class RouteListActivity : AppCompatActivity() {
    @Inject
    lateinit var mapsActivityDatabase: MapsActivityDatabase
    @Inject
    lateinit var mapsDao: MapsDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerActivityComponent.builder().activityModule(ActivityModule(this)).build().inject(this)
        setContentView(R.layout.activity_route_list)

        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=RouteDetailAdapter(mapsActivityDatabase.mapsDao().getDb())

    }
}