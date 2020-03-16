package com.codehunterz.isailing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class Place {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("properties")
    @Expose
    private var properties: PlaceProperties? = null

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null

    fun getProperties(): PlaceProperties? {
        return properties
    }

    fun setProperties(properties: PlaceProperties?) {
        this.properties = properties
    }

    override fun toString(): String {
        return ToStringBuilder(this).append("type", type).append("properties", properties)
            .append("geometry", geometry).toString()
    }
}