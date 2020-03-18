package com.codehunterz.isail.api.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Properties of a distinct Place
class Property {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("id")
    @Expose
    var id: Float? = null
}