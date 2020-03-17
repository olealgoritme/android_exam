package com.codehunterz.isail.api.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

// Place object with it's attributes
// API returns a JSON array with Place objects and attributes
class Place {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("properties")
    @Expose
    private var properties: Property? = null

    @SerializedName("geometry")
    @Expose
    var placeGeometry: Geometry? = null

    fun getProperties(): Property? {
        return properties
    }

    fun setProperties(properties: Property?) {
        this.properties = properties
    }

    override fun toString(): String {
        return ToStringBuilder(this).append("type", type).append("properties", properties)
            .append("geometry", placeGeometry).toString()
    }
}