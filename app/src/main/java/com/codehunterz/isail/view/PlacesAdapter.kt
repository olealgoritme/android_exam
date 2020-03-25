package com.codehunterz.isail.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.listener.OnPlaceClickListener
import com.codehunterz.isail.model.places.Place
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST")
class PlacesAdapter (private var list: MutableList<Place>, private var listener : OnPlaceClickListener)
    : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>(), Filterable {

    private var placeList = list;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val place: Place = list[position]
        holder.bind(place)
    }

    fun resetList() {
        list = placeList;
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(chars: CharSequence): FilterResults {

                val filteredList: MutableList<Place> = ArrayList()
                val filterResults = FilterResults()
                val filterPattern = chars.toString().toLowerCase(Locale.getDefault()).trim();

                if(chars.isEmpty()) {
                    filteredList.addAll(placeList)
                } else {
                    for (place in list) {
                        val placeName = place.getProperties()!!.name!!.toLowerCase(Locale.getDefault()).trim()
                        if (placeName.contains(filterPattern)) {
                            filteredList.add(place)
                        }
                    }
                }
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(c: CharSequence, result: FilterResults) {
                val newList: MutableList<Place> = result.values as MutableList<Place>
                list = newList
                //list.clear()
                //list.addAll(newList)
                notifyDataSetChanged()
            }
        }
    }

    inner class PlacesViewHolder (inflater: LayoutInflater, parent: ViewGroup)
        :RecyclerView.ViewHolder(inflater.inflate(R.layout.list_places_item, parent, false)) {
        private var textPlace: TextView

        init {
            val mPlaceName: TextView = itemView.findViewById(R.id.list_place_name)
            val mIcon: ImageView = itemView.findViewById(R.id.list_map_icon)
            textPlace = mPlaceName

            // Place Name click Listener
            mPlaceName.setOnClickListener {
                listener.onPlaceNameClicked(list[adapterPosition])
            }

            // Map Icon click listener
            mIcon.setOnClickListener {
                listener.onPlaceMapIconClicked(list[adapterPosition])
            }
        }

        fun bind(place: Place) {
            textPlace.text = place.getProperties()?.name
        }
    }
}
