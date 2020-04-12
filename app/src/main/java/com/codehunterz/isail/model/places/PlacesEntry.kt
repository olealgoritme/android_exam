package com.codehunterz.isail.model.places

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// JSON Object Response entry point for API endpoint "/places"
@Parcelize
class PlacesEntry(
    @SerializedName("features")
    @Expose var placeList: MutableList<Place>
) : Parcelable

