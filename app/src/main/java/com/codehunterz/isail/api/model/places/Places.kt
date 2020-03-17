package com.codehunterz.isail.api.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

// JSON Response entry point
class Places {
    @SerializedName("type")
    @Expose
    var type: String? = null;

    @SerializedName("features")
    @Expose
    var placeList: List<Place>? = null

    override fun toString(): String {
        return ToStringBuilder(this).append("type", type).append("placeList", placeList).toString()
    }
}