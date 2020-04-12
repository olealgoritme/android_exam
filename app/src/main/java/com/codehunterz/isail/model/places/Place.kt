package com.codehunterz.isail.model.places

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Place(
    @SerializedName("properties")
    @Expose var properties: Property,

    @SerializedName("geometry")
    @Expose var geometry: Geometry
) : Parcelable {
    // Little tiny get helpers
    fun getId(): Long = properties.id;
    fun getName(): String = properties.name
    fun getIcon(): String = properties.icon;
    fun getLat(): Double = geometry.coordinates[1] // WRONG IN THE API! LAT COMES SECOND, LNG COMES FIRST
    fun getLng(): Double = geometry.coordinates[0] // WRONG IN THE API! LAT COMES SECOND, LNG COMES FIRST

    @Parcelize
    class Property(
        @SerializedName("id")
        @Expose var id: Long,

        @SerializedName("name")
        @Expose var name: String,

        @SerializedName("icon")
        @Expose var icon: String
    ) : Parcelable

    @Parcelize
    class Geometry(
        @SerializedName("coordinates")
        @Expose var coordinates: List<Double>
    ) : Parcelable

    //override fun compareTo(other: Place): Int = compareValuesBy(this, other, { this.images != (null) && this.images!!.isNotEmpty() }, { other.images != (null) && other.images!!.isNotEmpty() })

}



