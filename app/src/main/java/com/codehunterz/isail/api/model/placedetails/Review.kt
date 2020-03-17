package com.codehunterz.isailing.api.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class Review {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("userId")
    @Expose
    var userId: Int? = null

    @SerializedName("userName")
    @Expose
    var userName: String? = null

    @SerializedName("placeId")
    @Expose
    var placeId: Int? = null

    @SerializedName("time")
    @Expose
    var time: Int? = null

    @SerializedName("stars")
    @Expose
    var stars: Int? = null

    @SerializedName("comments")
    @Expose
    var comments: String? = null

    @SerializedName("likes")
    @Expose
    var likes: List<Any>? = null

    override fun toString(): String {
        return ToStringBuilder(this).append("id", id).append("userId", userId)
            .append("userName", userName).append("placeId", placeId).append("time", time)
            .append("stars", stars).append("comments", comments).append("likes", likes).toString()
    }
}