package com.codehunterz.isail.model.placedetails

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PlaceDetails(
    @SerializedName("id")
    @Expose var id: Long,

    @SerializedName("name")
    @Expose var name: String,

    @SerializedName("lat")
    @Expose var lat: Double,

    @SerializedName("lon")
    @Expose var lon: Double,

    @SerializedName("countryCode")
    @Expose var countryCode: String,

    @SerializedName("comments")
    @Expose var comments: String,

    @SerializedName("col")
    @Expose var color: String,

    @SerializedName("icon")
    @Expose var icon: String,

    @SerializedName("stars")
    @Expose var stars: Int,

    @SerializedName("banner")
    @Expose var banner: String,

    @SerializedName("images")
    @Expose var images: List<Image>

) : Parcelable {

    @Parcelize
    class Image(
        @SerializedName("servingUrl")
        @Expose
        var servingUrl: String,

        @SerializedName("caption")
        @Expose var caption: String
    ) : Parcelable
}

