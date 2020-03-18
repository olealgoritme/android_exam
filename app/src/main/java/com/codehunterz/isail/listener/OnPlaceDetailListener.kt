package com.codehunterz.isail.listener

import com.codehunterz.isail.model.placedetails.PlaceDetails

interface OnPlaceDetailListener {
    fun onPlaceDetails(placeDetail : PlaceDetails)
    fun onPlaceDetailsError()
}