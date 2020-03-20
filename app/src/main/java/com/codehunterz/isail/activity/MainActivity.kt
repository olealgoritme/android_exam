package com.codehunterz.isail.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.api.AsyncRequest
import com.codehunterz.isail.listener.OnAPIRequestListener
import com.codehunterz.isail.listener.OnPlaceClickListener
import com.codehunterz.isail.model.placedetails.PlaceDetailsEntry
import com.codehunterz.isail.model.places.Place
import com.codehunterz.isail.model.places.PlacesEntry
import com.codehunterz.isail.view.PlacesAdapter
import com.google.android.material.snackbar.Snackbar


class MainActivity
    : AppCompatActivity(), OnAPIRequestListener, OnPlaceClickListener {

    private var recyclerView: RecyclerView? = null
    private var placesAdapter: PlacesAdapter? = null
    private var asyncReq: AsyncRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get RecyclerView reference
        recyclerView = findViewById(R.id.recyclerView)

        // Get all places from API
        apiGetAllPlaces()
    }

    override fun onAPIPlacesReqSuccess(placesEntry : PlacesEntry) {
        placesAdapter = placesEntry.placeList?.let {
            PlacesAdapter(it, this)
        }
        runOnUiThread {
            recyclerView?.adapter = placesAdapter
            recyclerView?.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onAPIDetailsReqSuccess(placeDetailsEntry: PlaceDetailsEntry) {
        Log.d(TAG, "onAPIDetailsReqSuccess(): ${placeDetailsEntry.placeDetails.toString()}")
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra(KEY_SELECTED_PLACE_DETAILS, placeDetailsEntry.placeDetails)
        startActivity(intent)
    }

    override fun onPlaceMapIconClicked(place: Place) {
        val intent = Intent(this@MainActivity, MapActivity::class.java)
        intent.putExtra(KEY_SELECTED_PLACE, place)
        startActivity(intent)
        Log.d(TAG, "Clicked PLACE ICON")
    }

    override fun onPlaceNameClicked(place: Place) {
        val placeId = place.getProperties()?.id!!;
        apiGetPlaceDetails(placeId)
        Log.d(TAG, "Clicked PLACE NAME with ID: $placeId")
    }

    override fun onAPIPlacesReqError() {
        showSnackMsg("ERROR when Requesting All Places from API. Please contact developer")
    }


    override fun onAPIDetailsReqError() {
        showSnackMsg("ERROR when Requesting Place Details from API. Please contact developer")
    }

    private fun showSnackMsg(msg : String) {
        val view: View = findViewById(android.R.id.content)
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction("OK") {}
            .show()
    }

    private fun apiGetAllPlaces() {
        // Send async get request to provider API (get ALL places)
        asyncReq = AsyncRequest()
        asyncReq!!.setParams(true);
        asyncReq!!.setListener(this)
        asyncReq!!.execute();
    }

    private fun apiGetPlaceDetails(placeId : String) {
        // Send async get request to prover API (get place details)
        asyncReq = AsyncRequest()
        asyncReq!!.setParams(false, placeId);
        asyncReq!!.setListener(this)
        asyncReq!!.execute();

    }

    companion object {
        const val KEY_SELECTED_PLACE: String = "SELECTED_PLACE"
        const val KEY_SELECTED_PLACE_DETAILS: String = "SELECTED_PLACE_DETAILS"
        private val TAG = MainActivity::class.java.simpleName

    }


}
