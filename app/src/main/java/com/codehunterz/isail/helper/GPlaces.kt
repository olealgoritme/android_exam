package com.codehunterz.isail.helper

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.codehunterz.isail.R
import com.codehunterz.isail.listener.OnGPlacesListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient

class GPlaces {


   fun getPlacePhotos(placeId: String) {
      if(placeId.isEmpty()) return

      val fields: List<Place.Field> = listOf(Place.Field.PHOTO_METADATAS)
      val placeRequest = FetchPlaceRequest.newInstance(placeId, fields)

      mPlacesClient!!.fetchPlace(placeRequest).addOnSuccessListener { response ->
         val place: Place = response.place
         val photoMetadata: PhotoMetadata? = place.photoMetadatas?.get(0) // Get first bitmap only
         //val attributions = photoMetadata?.attributions

      val photoRequest = photoMetadata?.let {
         FetchPhotoRequest.builder(it)
            .setMaxWidth(1280)
            .setMaxHeight(2000)
            .build()
      }

      mPlacesClient!!
         .fetchPhoto(photoRequest!!)

         .addOnSuccessListener { fetchPhotoResponse ->
            val bitmap: Bitmap = fetchPhotoResponse.bitmap
            Log.e("GPlaces", "fetched photo")
            mListener?.onPhotoReady(bitmap)
         }

         .addOnFailureListener {
           Log.e("GPlaces", "Failed to fetch photo")
         }
      }
         .addOnFailureListener {
            Log.e("GPlaces", "Failed to fetch place")
         }
   }

   fun getPlacesClient() : PlacesClient {
      return mPlacesClient!!
   }

   companion object {
      private var mInstance: GPlaces? = null
      private var mPlacesClient: PlacesClient? = null
      private var mListener: OnGPlacesListener? = null

      fun init(ctx: Context, listener : OnGPlacesListener? = null): GPlaces? {
         if(mInstance == null) {
            Places.initialize(ctx, ctx.getString(R.string.google_maps_key)) // Key
            mPlacesClient = Places.createClient(ctx)
            mListener = listener
         }
         return mInstance
      }

   }

}