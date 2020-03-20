package com.codehunterz.isail.model.placedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Image : Serializable {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("servingUrl")
    @Expose
    var servingUrl: String? = null

    @SerializedName("caption")
    @Expose
    var caption: String? = null

    @SerializedName("uploadedByUserId")
    @Expose
    var uploadedByUserId: String? = null

    @SerializedName("uploadedByUserDisplayName")
    @Expose
    var uploadedByUserDisplayName: String? = null

    @SerializedName("uploadedDate")
    @Expose
    var uploadedDate: String? = null

    @SerializedName("likes")
    @Expose
    var likes: List<String>? = null

    @SerializedName("width")
    @Expose
    var width: String? = null

    @SerializedName("height")
    @Expose
    var height: String? = null

}