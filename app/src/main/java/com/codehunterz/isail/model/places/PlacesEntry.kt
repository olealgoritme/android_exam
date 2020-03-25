package com.codehunterz.isail.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// JSON Response entry point
class PlacesEntry : Serializable {
    @SerializedName("features")
    @Expose
    var placeList: MutableList<Place>? = null
}