package com.codehunterz.isail.api.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.api.model.places.Place

class PlacesViewHolder (inflater: LayoutInflater, parent: ViewGroup)
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.list_places_item, parent, false)) {
    private var mPlaceName: TextView? = null
    private var mMapIcon: ImageView? = null

    init {
        mPlaceName = itemView.findViewById(R.id.list_place_name)
    }

    fun bind(place: Place) {
        mPlaceName?.text = place.getProperties()?.name
    }

}