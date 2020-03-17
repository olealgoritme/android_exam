package com.codehunterz.isailing

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isailing.api.model.places.Place
import java.util.*
import kotlin.collections.ArrayList

class PlacesAdapter(private val list: List<Place>)
    : RecyclerView.Adapter<PlacesViewHolder>(), Filterable {

    private var placeList: List<Place>? = null
    private var contactListFiltered: List<Place>? = null

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
                if (charString.isEmpty()) {
                    contactListFiltered = placeList
                } else {
                    val filteredList: MutableList<Place> = ArrayList()
                    for (row in list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getProperties()!!.name!!.toLowerCase(Locale.getDefault())
                                .contains(charString.toLowerCase(Locale.getDefault()))) {
                            filteredList.add(row)
                        }
                    }
                    contactListFiltered = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = contactListFiltered
                return filterResults
            }

            override fun publishResults(c: CharSequence, res: FilterResults) {
                contactListFiltered = res.values as List<Place>?
                notifyDataSetChanged()
            }
        }
    }

}
