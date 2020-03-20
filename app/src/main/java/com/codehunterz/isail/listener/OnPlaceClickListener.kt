package com.codehunterz.isail.listener

import com.codehunterz.isail.model.places.Place

interface OnPlaceClickListener {
    fun onPlaceMapIconClicked(place : Place)
    fun onPlaceNameClicked(place : Place)
}