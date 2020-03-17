package com.codehunterz.isailing.api.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class PlaceDetails {
    @SerializedName("snapshots")
    @Expose
    var snapshots: List<Any>? = null

    @SerializedName("place")
    @Expose
    var place: Place? = null

    override fun toString(): String {
        return ToStringBuilder(this).append("snapshots", snapshots).append("place", place)
            .toString()
    }
}