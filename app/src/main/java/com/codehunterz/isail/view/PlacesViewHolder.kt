package com.codehunterz.isail.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.model.places.Place

class PlacesViewHolder (inflater: LayoutInflater, parent: ViewGroup)
    :RecyclerView.ViewHolder(inflater.inflate(R.layout.list_places_item, parent, false)) {
    private var mPlaceName: TextView? = null
    private var mIcon: ImageView? = null

    // For passing on clicked place object
    var itemClick: ((Place) -> Unit)? = null
    private var places: List<Place> = emptyList()

    init {
        mPlaceName = itemView.findViewById(R.id.list_place_name)
        mIcon = itemView.findViewById(R.id.list_map_icon)

        // Click listener
        itemView.setOnClickListener {
            itemClick?.invoke(places[adapterPosition])
        }
    }

    fun bind(place: Place) {
        mPlaceName?.text = place.getProperties()?.name

    }


}