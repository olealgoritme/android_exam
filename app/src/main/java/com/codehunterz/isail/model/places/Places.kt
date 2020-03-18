package com.codehunterz.isail.api.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// JSON Response entry point
class Places {
    @SerializedName("type")
    @Expose
    var type: String? = null;

    @SerializedName("features")
    @Expose
    var placeList: List<Place>? = null
}