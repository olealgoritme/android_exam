package com.codehunterz.isail.listener

import android.view.View
import com.codehunterz.isail.model.places.Place

interface OnPlaceClickListener {
    fun onPlaceMapIconClicked(place : Place)
    fun onPlaceNameClicked(place : Place, view : View)
}