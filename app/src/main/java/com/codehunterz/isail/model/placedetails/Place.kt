package com.codehunterz.isail.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class Place {
    @SerializedName("id")
    @Expose
    var id: Int? = null

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
    var blogCount: Int? = null

    @SerializedName("externalLink1")
    @Expose
    var externalLink1: Any? = null

    @SerializedName("externalLinkDescription1")
    @Expose
    var externalLinkDescription1: Any? = null

    @SerializedName("externalLink2")
    @Expose
    var externalLink2: Any? = null

    @SerializedName("externalLinkDescription2")
    @Expose
    var externalLinkDescription2: Any? = null

    @SerializedName("dieselPrice")
    @Expose
    var dieselPrice: Int? = null

    @SerializedName("dieselPriceUpdatedMs")
    @Expose
    var dieselPriceUpdatedMs: Int? = null

    @SerializedName("gasolinePrice")
    @Expose
    var gasolinePrice: Int? = null

    @SerializedName("gasolinePriceUpdatedMs")
    @Expose
    var gasolinePriceUpdatedMs: Int? = null

    @SerializedName("maxLiftWeightTonnes")
    @Expose
    var maxLiftWeightTonnes: Int? = null

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
    var addedMs: Int? = null

    @SerializedName("addedBy")
    @Expose
    var addedBy: String? = null

    @SerializedName("addedById")
    @Expose
    var addedById: Any? = null

    @SerializedName("updatedMs")
    @Expose
    var updatedMs: Int? = null

    @SerializedName("updatedBy")
    @Expose
    var updatedBy: String? = null

    @SerializedName("updatedById")
    @Expose
    var updatedById: Any? = null

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
    var stars: Int? = null

    @SerializedName("banner")
    @Expose
    var banner: String? = null

    @SerializedName("images")
    @Expose
    var images: List<Any>? = null

    @SerializedName("reviews")
    @Expose
    var reviews: List<Review>? = null

    override fun toString(): String {
        return ToStringBuilder(this).append("id", id).append("type", type).append("name", name)
            .append("lat", lat).append("lon", lon).append("countryCode", countryCode)
            .append("comments", comments).append("meta", meta).append("blogCount", blogCount)
            .append("externalLink1", externalLink1)
            .append("externalLinkDescription1", externalLinkDescription1)
            .append("externalLink2", externalLink2)
            .append("externalLinkDescription2", externalLinkDescription2)
            .append("dieselPrice", dieselPrice).append("dieselPriceUpdatedMs", dieselPriceUpdatedMs)
            .append("gasolinePrice", gasolinePrice)
            .append("gasolinePriceUpdatedMs", gasolinePriceUpdatedMs)
            .append("maxLiftWeightTonnes", maxLiftWeightTonnes)
            .append("allowsExternalContractors", allowsExternalContractors)
            .append("canWorkOnOwnBoat", canWorkOnOwnBoat)
            .append("canStayOnOwnBoat", canStayOnOwnBoat)
            .append("priceBandHighSeason", priceBandHighSeason)
            .append("priceBandLowSeason", priceBandLowSeason)
            .append("winterCommunity", winterCommunity).append("protectionFrom", protectionFrom)
            .append("addedMs", addedMs).append("addedBy", addedBy).append("addedById", addedById)
            .append("updatedMs", updatedMs).append("updatedBy", updatedBy)
            .append("updatedById", updatedById).append("col", col).append("icon", icon)
            .append("mapboxIcon", mapboxIcon).append("stars", stars).append("banner", banner)
            .append("images", images).append("reviews", reviews).toString()
    }
}