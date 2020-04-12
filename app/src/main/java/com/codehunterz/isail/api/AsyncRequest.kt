package com.codehunterz.isail.api

import android.os.AsyncTask
import android.util.Log
import com.codehunterz.isail.listener.OnAPIRequestListener
import com.codehunterz.isail.model.placedetails.PlaceDetailsEntry
import com.codehunterz.isail.model.places.PlacesEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AsyncRequest
    : AsyncTask<Void, String, Void> () {

    private var apiRequestListener: OnAPIRequestListener? = null
    private var shouldGetAll: Boolean? = null
    private var placeId: Long? = null

    fun setParams(shouldGetAll: Boolean, placeId: Long? = null) {
        this.shouldGetAll = shouldGetAll
        this.placeId = placeId
    }

    fun setListener(apiRequestListener: OnAPIRequestListener) {
        this.apiRequestListener = apiRequestListener
    }

    override fun doInBackground(vararg params: Void?): Void? {

        // Sanity check
        preInitCheck();

        // Get API client / Service reference
        val apiClient = APIClient.getIt
        val service = apiClient?.create(APIService::class.java)!!

        // Switching between getting ALL Places and just Place details
        if(shouldGetAll as Boolean) {
            getAllPlaces(service)
        } else {
            if(placeId == null) throw Exception("Missing Place ID!")
            getPlaceDetails(service, placeId!!)
        }
        return null;
    }

    override fun onPostExecute(result: Void?) {
       // Empty stub. Not updating UI here
    }

    private fun getAllPlaces(service : APIService) {
        Log.d("AsyncRequest", "Getting ALL places");
        val call = service.getAllPlaces()

        call.enqueue(object : Callback<PlacesEntry> {

            override fun onFailure(call: Call<PlacesEntry>, t: Throwable) { apiRequestListener?.onAPIPlacesReqError(); }

            override fun onResponse(call: Call<PlacesEntry>, response: Response<PlacesEntry>) {
                if(response.isSuccessful) response.body()?.let { apiRequestListener?.onAPIPlacesReqSuccess(it.placeList) }
                else apiRequestListener?.onAPIPlacesReqError()
            }
        });
    }

    private fun getPlaceDetails(service : APIService, placeId : Long) {

        Log.d("AsyncRequest", "Getting PLACE DETAILS: $placeId");
        val call:Call<PlaceDetailsEntry> = service.getPlaceDetails(placeId)

        call.enqueue(object : Callback<PlaceDetailsEntry> {

            override fun onFailure(call: Call<PlaceDetailsEntry>, t: Throwable) { apiRequestListener?.onAPIDetailsReqError(); }

            override fun onResponse(call: Call<PlaceDetailsEntry>, response: Response<PlaceDetailsEntry>) {
                if(response.isSuccessful) response.body()?.let { apiRequestListener?.onAPIDetailsReqSuccess(it.placeDetails) }
                else apiRequestListener?.onAPIDetailsReqError()
            }
        });
    }

    private fun preInitCheck() {
        if (shouldGetAll == null) throw Exception("need to call .setParams() first");
        if (apiRequestListener == null) throw Exception("need to call .setListener() first")
    }

}