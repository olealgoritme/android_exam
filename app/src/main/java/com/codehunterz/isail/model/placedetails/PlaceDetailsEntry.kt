package com.codehunterz.isail.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlaceDetailsEntry : Serializable {
    @SerializedName("snapshots")
    @Expose
    var snapshots: List<Snapshot>? = null

    @SerializedName("place")
    @Expose
    var placeDetails: PlaceDetails? = null

}