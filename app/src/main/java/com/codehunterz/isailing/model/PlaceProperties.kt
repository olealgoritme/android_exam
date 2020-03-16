package com.codehunterz.isailing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class PlaceProperties {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    override fun toString(): String {
        return ToStringBuilder(this).append("name", name).append("icon", icon).append("id", id)
            .toString()
    }
}