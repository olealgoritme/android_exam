package com.codehunterz.isail.listener

import com.codehunterz.isail.model.places.Places

interface OnPlacesRequestListener {
    fun onPlacesRequestSuccess(places : Places)
    fun onPlacesRequestError()
}