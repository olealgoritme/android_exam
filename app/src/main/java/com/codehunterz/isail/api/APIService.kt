package com.codehunterz.isail.api

import com.codehunterz.isail.api.model.places.Place
import com.codehunterz.isail.api.model.places.Places
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("places")
    fun getAllPlaces() : Call<Places>

    @GET("place")
    fun getPlaceDetails(@Query("id") format: String) : Call<Place>
}