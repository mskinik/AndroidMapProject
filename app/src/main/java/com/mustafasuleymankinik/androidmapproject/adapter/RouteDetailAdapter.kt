package com.mustafasuleymankinik.androidmapproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mustafasuleymankinik.androidmapproject.R
import com.mustafasuleymankinik.androidmapproject.db.RouteDetail
import kotlinx.android.synthetic.main.route_list.view.*

class RouteDetailAdapter(val routeDetail:List<RouteDetail>): RecyclerView.Adapter<RouteDetailAdapter.ViewHolder>() {
    val adapterItem=routeDetail
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view=  LayoutInflater.from(parent.context).inflate(R.layout.route_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return adapterItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.startIdTextView.text=routeDetail[position].startId
        holder.itemView.endIdTextView.text=routeDetail[position].endId
        holder.itemView.distanceTextView.text=routeDetail[position].distance.toString()
    }
}