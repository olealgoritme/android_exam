package com.codehunterz.isail.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlaceDetails : Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lon")
    @Expose
    var lon: Double? = null

    @SerializedName("countryCode")
    @Expose
    var countryCode: String? = null

    @SerializedName("comments")
    @Expose
    var comments: String? = null

    @SerializedName("meta")
    @Expose
    var meta: Meta? = null

    @SerializedName("blogCount")
    @Expose
    var blogCount: String? = null

    @SerializedName("externalLink1")
    @Expose
    var externalLink1: String? = null

    @SerializedName("externalLinkDescription1")
    @Expose
    var externalLinkDescription1: String? = null

    @SerializedName("externalLink2")
    @Expose
    var externalLink2: String? = null

    @SerializedName("externalLinkDescription2")
    @Expose
    var externalLinkDescription2: String? = null

    @SerializedName("dieselPrice")
    @Expose
    var dieselPrice: String? = null

    @SerializedName("dieselPriceUpdatedMs")
    @Expose
    var dieselPriceUpdatedMs: String? = null

    @SerializedName("gasolinePrice")
    @Expose
    var gasolinePrice: String? = null

    @SerializedName("gasolinePriceUpdatedMs")
    @Expose
    var gasolinePriceUpdatedMs: String? = null

    @SerializedName("maxLiftWeightTonnes")
    @Expose
    var maxLiftWeightTonnes: String? = null

    @SerializedName("allowsExternalContractors")
    @Expose
    var allowsExternalContractors: Boolean? = null

    @SerializedName("canWorkOnOwnBoat")
    @Expose
    var canWorkOnOwnBoat: Boolean? = null

    @SerializedName("canStayOnOwnBoat")
    @Expose
    var canStayOnOwnBoat: Boolean? = null

    @SerializedName("priceBandHighSeason")
    @Expose
    var priceBandHighSeason: String? = null

    @SerializedName("priceBandLowSeason")
    @Expose
    var priceBandLowSeason: String? = null

    @SerializedName("winterCommunity")
    @Expose
    var winterCommunity: Boolean? = null

    @SerializedName("protectionFrom")
    @Expose
    var protectionFrom: String? = null

    @SerializedName("addedMs")
    @Expose
    var addedMs: String? = null

    @SerializedName("addedBy")
    @Expose
    var addedBy: String? = null

    @SerializedName("addedById")
    @Expose
    var addedById: String? = null

    @SerializedName("updatedMs")
    @Expose
    var updatedMs: String? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: String? = null

    @SerializedName("updatedById")
    @Expose
    var updatedById: String? = null

    @SerializedName("col")
    @Expose
    var col: String? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("mapboxIcon")
    @Expose
    var mapboxIcon: String? = null

    @SerializedName("stars")
    @Expose
    var stars: String? = null

    @SerializedName("banner")
    @Expose
    var banner: String? = null

    @SerializedName("images")
    @Expose
    var images: List<Image>? =
        null

    @SerializedName("reviews")
    @Expose
    var reviews: List<Any>? = null

}