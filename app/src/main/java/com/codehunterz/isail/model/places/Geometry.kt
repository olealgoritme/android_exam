package com.codehunterz.isail.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Geometry of a distinct Place
class Geometry(lat: Double, lng: Double) : Serializable {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("coordinates")
    @Expose
    var coordinates: List<Double>? = null

    init {
        this.coordinates = listOf(lat, lng)
    }
}