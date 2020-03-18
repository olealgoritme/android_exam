package com.codehunterz.isail.api.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.api.model.places.Place
import java.util.*
import kotlin.collections.ArrayList

class PlacesAdapter(private var list: List<Place>)
    : RecyclerView.Adapter<PlacesViewHolder>(), Filterable {

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

}
