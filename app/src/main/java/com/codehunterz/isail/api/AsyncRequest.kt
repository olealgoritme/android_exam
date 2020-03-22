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
    private var shouldGetAll: Boolean? = false
    private var placeId: String? = null

    fun setParams(shouldGetAll: Boolean, placeId: String? = null) {
        this.shouldGetAll = shouldGetAll
        this.placeId = placeId
    }

    fun setListener(apiRequestListener: OnAPIRequestListener) {
        this.apiRequestListener = apiRequestListener
    }

    override fun doInBackground(vararg params: Void?): Void? {

        if(!shouldGetAll!! && placeId!!.isEmpty())
            throw Exception("need to call .setParams() first");

        val apiClient = APIClient.getIt
        val service = apiClient?.create(APIService::class.java)!!

        // Switch between getAllPlaces() and getPlaceDetails()
        if(shouldGetAll as Boolean) {
            Log.d("AsyncRequest", "Getting ALL places");
            val call = service.getAllPlaces()
            call.enqueue(object : Callback<PlacesEntry> {
                override fun onFailure(call: Call<PlacesEntry>, t: Throwable) {
                    apiRequestListener?.onAPIPlacesReqError();
                    Log.e("Async", "Throw: $t")
                }
                override fun onResponse(call: Call<PlacesEntry>, response: Response<PlacesEntry>) {
                    if(response.isSuccessful) response.body()?.let {
                        apiRequestListener?.onAPIPlacesReqSuccess(it)
                    }
                    else apiRequestListener?.onAPIPlacesReqError()
                    Log.e("Async", "response: $response")
                }
            });
        } else {
            Log.d("AsyncRequest", "Getting PLACE DETAILS: " + placeId!!);
            val call = service.getPlaceDetails(placeId!!)
            call.enqueue(object : Callback<PlaceDetailsEntry> {
                override fun onFailure(call: Call<PlaceDetailsEntry>, t: Throwable) {
                    apiRequestListener?.onAPIDetailsReqError();
                    Log.e("Async", "Throw: $t")
                }
                override fun onResponse(call: Call<PlaceDetailsEntry>, response: Response<PlaceDetailsEntry>) {
                    if(response.isSuccessful) response.body()?.let {
                        apiRequestListener?.onAPIDetailsReqSuccess(it)
                    }
                    else
                        apiRequestListener?.onAPIDetailsReqError()
                    Log.e("Async", "response: $response")
                }
            });

        }
        return null;
    }

    override fun onPostExecute(result: Void?) {
    }
}