package com.codehunterz.isail.listener

import com.codehunterz.isail.model.placedetails.PlaceDetails
import com.codehunterz.isail.model.places.Place

interface OnAPIRequestListener {
    fun onAPIPlacesReqSuccess(placeList: MutableList<Place>)
    fun onAPIPlacesReqError()

    fun onAPIDetailsReqSuccess(placeDetails: PlaceDetails)
    fun onAPIDetailsReqError()
}