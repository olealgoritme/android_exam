package com.codehunterz.isail.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlaceDetailsEntry(
    @SerializedName("snapshots")
    @Expose var snapshots: List<Snapshot>,

    @SerializedName("place")
    @Expose var placeDetails: PlaceDetails
) : Serializable {

    class Snapshot(
        @SerializedName("time")
        @Expose var time: String,

        @SerializedName("reason")
        @Expose var reason: String
    ) : Serializable
}