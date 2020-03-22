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

class PlacesAdapter (private var list: List<Place>, private var listener : OnPlaceClickListener)
    : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlacesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val place: Place = list[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filteredList: MutableList<Place> = ArrayList()
                    for (row in list) {
                        if (row.getProperties()?.name!!.toLowerCase(Locale.getDefault())
                                .contains(charString.toLowerCase(Locale.getDefault()))) {
                            filteredList.add(row)
                        }
                    }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(c: CharSequence, res: FilterResults) {
                list = res.values as List<Place>
                notifyDataSetChanged()
            }
        }
    }

    inner class PlacesViewHolder (inflater: LayoutInflater, parent: ViewGroup)
        :RecyclerView.ViewHolder(inflater.inflate(R.layout.list_places_item, parent, false)) {
        private var textPlace: TextView? = null

        init {
            val mPlaceName: TextView? = itemView.findViewById(R.id.list_place_name)
            val mIcon: ImageView? = itemView.findViewById(R.id.list_map_icon)
            textPlace = mPlaceName

            // Place Name click Listener
            mPlaceName?.setOnClickListener {
                listener.onPlaceNameClicked(list[adapterPosition])
            }

            // Map Icon click listener
            mIcon?.setOnClickListener {
                listener.onPlaceMapIconClicked(list[adapterPosition])
            }
        }

        fun bind(place: Place) {
            textPlace?.text = place.getProperties()?.name
        }
    }

    companion object {
        private val TAG = PlacesAdapter::class.java.simpleName
    }

}
