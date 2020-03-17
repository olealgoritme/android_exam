package com.codehunterz.isail.api.listener

import com.codehunterz.isail.api.model.places.Places

interface OnPlacesListener {
    fun onPlaces(places : Places)
    fun onPlacesError()
}