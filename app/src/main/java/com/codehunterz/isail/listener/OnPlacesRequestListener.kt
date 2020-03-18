package com.codehunterz.isail.api.listener

import com.codehunterz.isail.api.model.places.Places

interface OnPlacesRequestListener {
    fun onPlacesRequestSuccess(places : Places)
    fun onPlacesRequestError()
}