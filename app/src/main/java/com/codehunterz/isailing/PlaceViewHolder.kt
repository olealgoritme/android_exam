package com.codehunterz.isailing

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isailing.model.Place

class PlaceViewHolder (inflater: LayoutInflater, parent: ViewGroup) :
RecyclerView.ViewHolder(inflater.inflate(R.layout.list_place_item, parent, false)) {
    private var mPlaceName: TextView? = null
    private var mMapIcon: ImageView? = null

    init {
        mPlaceName = itemView.findViewById(R.id.list_place_name)
        mMapIcon = itemView.findViewById(R.id.list_map_icon)
    }

    fun bind(place: Place) {
        mPlaceName?.text = place.getProperties()?.name
        //mMapIcon?.setImageResource()
    }

}