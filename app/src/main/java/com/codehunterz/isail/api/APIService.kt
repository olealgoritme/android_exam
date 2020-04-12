package com.codehunterz.isail.api

import com.codehunterz.isail.model.placedetails.PlaceDetailsEntry
import com.codehunterz.isail.model.places.PlacesEntry
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("places")
    fun getAllPlaces() : Call<PlacesEntry>

    @GET("place")
    fun getPlaceDetails(@Query("id") format: Long) : Call<PlaceDetailsEntry>
}