package com.codehunterz.isailing.api.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

// Geometry of a distinct Place
class Geometry {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("coordinates")
    @Expose
    var coordinates: List<Double>? = null

    override fun toString(): String {
        return ToStringBuilder(this).append("type", type).append("coordinates", coordinates)
            .toString()
    }
}