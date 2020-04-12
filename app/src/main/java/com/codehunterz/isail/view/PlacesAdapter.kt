package com.codehunterz.isail.view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.helper.IconDetails
import com.codehunterz.isail.listener.OnPlaceClickListener
import com.codehunterz.isail.model.places.Place
import com.codehunterz.isail.view.neumorphism.NeumorphCardView
import java.util.*
import kotlin.collections.ArrayList


@RequiresApi(Build.VERSION_CODES.O)
class PlacesAdapter(private var list: MutableList<Place>, private var listener: OnPlaceClickListener)
    : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>(), Filterable {

    private var placeList = list
    private var ctx: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        ctx = parent.context
        return PlacesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val place: Place = list[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(chars: CharSequence): FilterResults {
                val tempList = placeList
                val filterResults = FilterResults()
                val filteredList: MutableList<Place?> = ArrayList()

                if(chars.isEmpty()) {
                    filteredList.addAll(tempList)
                } else {
                    val filterPattern = chars.toString().toLowerCase(Locale.getDefault()).trim()
                    tempList.map { place ->
                        if(place.getName().toLowerCase(Locale.getDefault())
                                .trim()
                                .contains(filterPattern)) {
                            filteredList.add(place)
                        }
                    }
                }

                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(c: CharSequence, result: FilterResults) {
                val newList = result.values as MutableList<Place>
                if(!newList.isNullOrEmpty()) {
                    list = newList
                    notifyDataSetChanged()
                }
            }
        }
    }

    inner class PlacesViewHolder (inflater: LayoutInflater, parent: ViewGroup)
        :RecyclerView.ViewHolder(inflater.inflate(R.layout.list_places_item, parent, false)) {
        private var mIconType: ImageView = itemView.findViewById(R.id.iconType)
        private var mPlaceName: TextView = itemView.findViewById(R.id.placeName)
        private var mIconLayout: NeumorphCardView = itemView.findViewById(R.id.list_icons)
        private var mPlaceCard: NeumorphCardView = itemView.findViewById(R.id.card)

        init {

            // Place Name click Listener
            mPlaceCard.setOnClickListener { listener.onPlaceNameClicked(list[adapterPosition], mPlaceName) }

            // Map Icon layout click listener
            mIconLayout.setOnClickListener { listener.onPlaceMapIconClicked(list[adapterPosition]) }

        }

        fun bind(place: Place) {
            mPlaceName.text = place.getName()
            if(place.getIcon().isNotEmpty()) {
                // Find resource id based on string name (nfl_anchorage, etc)
                var iconName = place.getIcon().toLowerCase(Locale.getDefault())
                iconName = iconName.replace("nfl_", "")
                    .replace(".png", "")
                val resId = ctx!!.resources.getIdentifier(iconName, "drawable", ctx!!.packageName)
                mIconType.setImageResource(resId)
            }
            mIconType.tooltipText = IconDetails.getDetails(place.getIcon())

            /*
            // TODO: guess country based on LAT/LNG coordinates
            val gcd = Geocoder(ctx, Locale.getDefault())
            val addresses: List<Address> = gcd.getFromLocation(place.getLat(), place.getLng(), 1)
            if (addresses.isNotEmpty()) {
                val countryCode =  addresses[0].countryCode
                Log.d("PlacesAdapter", "Country: " + addresses[0].countryName + " Code: " + countryCode.toLowerCase(Locale.getDefault()))
                if(countryCode != null) {
                    val countryResId = ctx!!.resources.getIdentifier(countryCode.toLowerCase(Locale.getDefault()), "drawable", ctx!!.packageName)
                    mIconCountry.setImageResource(countryResId)
                }
            }
             */
        }
    }
}
