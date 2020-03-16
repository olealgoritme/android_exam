package com.codehunterz.isailing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

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