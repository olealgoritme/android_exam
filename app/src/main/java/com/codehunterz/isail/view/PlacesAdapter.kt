package com.codehunterz.isail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.model.places.Place
import java.util.*
import kotlin.collections.ArrayList

class PlacesAdapter (private var list: List<Place>)
    : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>(), Filterable {


    // For passing on clicked place object
    var itemClick: ((Place) -> Unit)? = null
    private var places: List<Place> = emptyList()

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
                        if (row.getProperties()!!.name!!.toLowerCase(Locale.getDefault())
                                .contains(charString.toLowerCase(Locale.getDefault()))) {
                            filteredList.add(row)
                        }
                    }
                val filterResults = FilterResults()
                filterResults.values = filterResults
                return filterResults
            }

            override fun publishResults(c: CharSequence, res: FilterResults) {
                list = (res.values as List<Place>?)!!
                notifyDataSetChanged()
            }
        }
    }

    inner class PlacesViewHolder (inflater: LayoutInflater, parent: ViewGroup)
        :RecyclerView.ViewHolder(inflater.inflate(R.layout.list_places_item, parent, false)) {
        private var mPlaceName: TextView? = null
        private var mIcon: ImageView? = null


        init {
            mPlaceName = itemView.findViewById(R.id.list_place_name)
            mIcon = itemView.findViewById(R.id.list_map_icon)

            // Click listener
            itemView.setOnClickListener {
                itemClick?.invoke(list[adapterPosition])
            }
        }

        fun bind(place: Place) {
            mPlaceName?.text = place.getProperties()?.name

        }

    }

}
