package com.codehunterz.isail.listener

import com.codehunterz.isail.model.placedetails.PlaceDetailsEntry
import com.codehunterz.isail.model.places.PlacesEntry

interface OnAPIRequestListener {
    fun onAPIPlacesReqSuccess(placesEntry : PlacesEntry)
    fun onAPIPlacesReqError()

    fun onAPIDetailsReqSuccess(placeDetailsEntry : PlaceDetailsEntry)
    fun onAPIDetailsReqError()
}