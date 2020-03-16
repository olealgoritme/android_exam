package com.codehunterz.isailing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isailing.model.Place

class PlaceAdapter(private val list: List<Place>)
    : RecyclerView.Adapter<PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaceViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place: Place = list[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int = list.size

}
