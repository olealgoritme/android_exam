package com.codehunterz.isail.api

import android.os.AsyncTask
import com.codehunterz.isail.listener.OnPlacesRequestListener
import com.codehunterz.isail.model.places.Places
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AsyncRequest
    : AsyncTask<Void, Void, Void> () {

    private var placesListener: OnPlacesRequestListener? = null

    fun setListener(placesRequestListener: OnPlacesRequestListener?) {
        this.placesListener = placesRequestListener
    }

    override fun doInBackground(vararg params: Void): Void? {
        val apiClient = APIClient.getIt
        val service = apiClient?.create(APIService::class.java)!!
        val call = service.getAllPlaces()

        // val response = call.execute(); if(response.isSuccessful) response.body()?.let { placesListener?.onPlaces(it) } else placesListener?.onPlacesError()

        call.enqueue(object : Callback<Places> {

            override fun onFailure(call: Call<Places>, t: Throwable) {
                placesListener?.onPlacesRequestError();
            }

            override fun onResponse(call: Call<Places>, response: Response<Places>) {
                if(response.isSuccessful)
                    response.body()?.let { placesListener?.onPlacesRequestSuccess(it) }
                else
                    placesListener?.onPlacesRequestError()
            }
        });

        return null;
    }

    override fun onPostExecute(result: Void?) {
    }
}