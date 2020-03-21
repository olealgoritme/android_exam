package com.codehunterz.isail.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Place object with it's attributes
// API returns a JSON array with Place objects and attributes
class Place(
    @SerializedName("properties")
    @Expose
    private var properties: Property?, @SerializedName("geometry")
    @Expose var geometry: Geometry?
) : Serializable {

    fun getProperties(): Property? {
        return properties
    }

    fun setProperties(properties: Property?) {
        this.properties = properties
    }

}

