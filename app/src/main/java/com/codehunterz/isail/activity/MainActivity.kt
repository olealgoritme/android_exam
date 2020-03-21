package com.codehunterz.isail.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.api.AsyncRequest
import com.codehunterz.isail.db.PlaceDao
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
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get RecyclerView reference
        recyclerView = findViewById(R.id.recyclerView)

        // Reference ProgressBar
        progressBar = findViewById(R.id.progressBar)

        // Fetch Places from API or DB
        fetchPlaces()
    }

    override fun onAPIPlacesReqSuccess(placesEntry : PlacesEntry) {
        progressBar?.visibility = View.GONE

        placesEntry.placeList?.let {
            populateRecyclerView(it) // list -> PlacesAdapter -> RecyclerView
            insertPlacesToDb(it)     // list -> Database
        }
    }

    override fun onAPIDetailsReqSuccess(placeDetailsEntry: PlaceDetailsEntry) {
        Log.d(TAG, "onAPIDetailsReqSuccess(): ${placeDetailsEntry.placeDetails.toString()}")
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra(KEY_SELECTED_PLACE_DETAILS, placeDetailsEntry.placeDetails)
        startActivity(intent)
    }


    override fun onAPIPlacesReqError() {
        progressBar?.visibility = View.GONE
        showSnackMsg("ERROR when Requesting All Places from API. Please contact developer")
    }


    override fun onAPIDetailsReqError() {
        showSnackMsg("ERROR when Requesting Place Details from API. Please contact developer")
    }

    override fun onPlaceMapIconClicked(place: Place) {
        val intent = Intent(this@MainActivity, MapActivity::class.java)
        intent.putExtra(KEY_SELECTED_PLACE, place)
        startActivity(intent)
        Log.d(TAG, "Clicked PLACE ICON")
    }

    override fun onPlaceNameClicked(place: Place) {
        val placeId = place.getProperties()?.id
        placeId?.let { fetchPlaceDetails(it) }
        Log.d(TAG, "Clicked PLACE NAME with ID: $placeId")
    }

    private fun showSnackMsg(msg : String) {
        val view: View = findViewById(android.R.id.content)
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction("OK") {}
            .show()
    }

    private fun fetchPlaces() {
        // Check if we already have places in our database
        Thread(Runnable {
            val placeList = PlaceDao(this).fetchAll()

            // Send Async API request if list from DB is empty
            if(placeList.isEmpty()) {
                Log.d(TAG, "Place List from DB was empty -- GETTING PLACES FROM API ")
                // Send async get request to provider API (get ALL places)
                asyncReq = AsyncRequest()
                asyncReq!!.setParams(true);
                asyncReq!!.setListener(this)
                asyncReq!!.execute();
            } else {
                Log.d(TAG, "Place List from DB FULL -- GETTING PLACES FROM DB")
                // Send List -> PlacesAdapter -> RecyclerView
                populateRecyclerView(placeList)
            }
                progressBar?.visibility = View.GONE

        }).run()
    }

    private fun fetchPlaceDetails(placeId : String) {
        // Send async get request to prover API (get place details)
        asyncReq = AsyncRequest()
        asyncReq!!.setParams(false, placeId);
        asyncReq!!.setListener(this)
        asyncReq!!.execute();

    }

    private fun insertPlacesToDb(placeList: List<Place>) {
        // Inserting all places into DB (fire and forget Thread)
        Thread(Runnable {
            insertPerformance(placeList)
            showSnackMsg("Number of places in database: " + PlaceDao(this).fetchAll().size)
        }).run()
    }

    private fun populateRecyclerView(placesList : List<Place>) {
        // Set PlacesAdapter in RecyclerView (fire and forget Thread)
        Thread(Runnable {
            placesAdapter = PlacesAdapter(placesList, this@MainActivity)
            recyclerView?.adapter = placesAdapter
            recyclerView?.layoutManager = LinearLayoutManager(this@MainActivity)
        }).run()
    }

    private fun insertPerformance(placeList: List<Place>) {
        // "Performance" because it uses transactions
        PlaceDao(this).insertList(placeList)
    }

    companion object {
        const val KEY_SELECTED_PLACE: String = "SELECTED_PLACE"
        const val KEY_SELECTED_PLACE_DETAILS: String = "SELECTED_PLACE_DETAILS"
        private val TAG = MainActivity::class.java.simpleName
    }
}
