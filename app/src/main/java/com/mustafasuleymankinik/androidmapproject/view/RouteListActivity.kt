package com.mustafasuleymankinik.androidmapproject.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafasuleymankinik.androidmapproject.DaggerActivityComponent

import com.mustafasuleymankinik.androidmapproject.R
import com.mustafasuleymankinik.androidmapproject.adapter.RouteDetailAdapter
import com.mustafasuleymankinik.androidmapproject.contract.RouteListActivityContract
import com.mustafasuleymankinik.androidmapproject.db.MapsActivityDatabase
import com.mustafasuleymankinik.androidmapproject.db.MapsDao
import com.mustafasuleymankinik.androidmapproject.db.RouteDetail
import com.mustafasuleymankinik.androidmapproject.module.ActivityModule
import com.mustafasuleymankinik.androidmapproject.presenter.RouteListActivityPresenter
import kotlinx.android.synthetic.main.activity_route_list.*
import javax.inject.Inject

class RouteListActivity : AppCompatActivity(),RouteListActivityContract.View {

    @Inject
    lateinit var routeListActivityPresenter: RouteListActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerActivityComponent.builder().activityModule(ActivityModule(this)).build().inject(this)
        setContentView(R.layout.activity_route_list)

        routeListActivityPresenter.setView(this)
        routeListActivityPresenter.dbInit()

    }

    override fun setInit(listRoute:List<RouteDetail>) {
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=RouteDetailAdapter(listRoute)
    }
}