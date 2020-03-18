package com.codehunterz.isail.api.listener

import com.codehunterz.isail.api.model.placedetails.PlaceDetails

interface OnPlaceDetailListener {
    fun onPlaceDetails(placeDetail : PlaceDetails)
    fun onPlaceDetailsError()
}