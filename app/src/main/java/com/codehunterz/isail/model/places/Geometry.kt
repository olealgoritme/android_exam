package com.codehunterz.isail.api.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Geometry of a distinct Place
class Geometry {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("coordinates")
    @Expose
    var coordinates: List<Double>? = null
}