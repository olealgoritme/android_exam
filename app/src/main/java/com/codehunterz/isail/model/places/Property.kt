package com.codehunterz.isail.model.places

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

// Properties of a distinct Place
class Property(
    @SerializedName("id")
    @Expose var id: String?,

    @SerializedName("name")
    @Expose var name: String?,

    @SerializedName("icon")
    @Expose var icon: String?
) : Serializable

