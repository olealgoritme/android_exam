package com.codehunterz.isail.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.codehunterz.isail.R
import com.codehunterz.isail.api.AsyncRequest
import com.codehunterz.isail.db.PlaceDao
import com.codehunterz.isail.listener.OnAPIRequestListener
import com.codehunterz.isail.listener.OnPlaceClickListener
import com.codehunterz.isail.model.placedetails.PlaceDetails
import com.codehunterz.isail.model.places.Place
import com.codehunterz.isail.view.PlacesAdapter


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity
    : AppCompatActivity(), OnAPIRequestListener, OnPlaceClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var placesAdapter: PlacesAdapter

    private lateinit var asyncReq: AsyncRequest
    private lateinit var searchView: SearchView

    private lateinit var alertDialog: AlertDialog // Disable UI while loading
    private lateinit var progressBar: LottieAnimationView

    // View for transitioning snatched from Adapter and shared -> next Activity
    private var transitionView: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable transitions (doing it both here and in theme definition
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        setContentView(R.layout.activity_main)

        // RecyclerView reference
        recyclerView = findViewById(R.id.recyclerView)

        // ProgressBar reference
        progressBar = findViewById(R.id.progressBar)
        alertDialog = AlertDialog.Builder(this).setCancelable(false).create()
        showProgressBar(false)



        Thread().run {
            val placeList: MutableList<Place> = PlaceDao().fetchAll()
            // Populate RecyclerView
            populateRecyclerView(placeList)
        }
    }

    // Hides progressbar
    override fun onStart() {
        super.onStart()
        showProgressBar(false)
    }

    // Hides progressbar
    override fun onResume() {
        super.onResume()
        showProgressBar(false)
    }

    // Hides progressbar
    override fun onPause() {
        super.onPause()
        showProgressBar(false)
    }

    // Sanity checking that SearchView returns to correct view state
    // Without this, the activity will close instead of closing the
    // actual SearchView
    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.onActionViewCollapsed()
        } else {
            super.onBackPressed()
        }
    }

    // Creates option menu (for SearchView) in ActionBar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search_bar, menu)
        val searchItem = menu.findItem(R.id.actionSearch)

        searchView = searchItem.actionView as SearchView
        searchView.setOnCloseListener { true }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                placesAdapter.filter.filter(query)
                // In case user is in middle of list after search
                smoothScrollTop()
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                placesAdapter.filter.filter(newText)
                // In case user is in middle of list after search
                smoothScrollTop()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    // Gets called when API Details request returned correct object
    override fun onAPIDetailsReqSuccess(placeDetails: PlaceDetails) {
        Log.d(TAG, "onAPIDetailsReqSuccess(): $placeDetails")
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)

        // Set PlaceDetails object
        intent.putExtra(KEY_SELECTED_PLACE_DETAILS, placeDetails)

        // Set View for scene transition
        intent.putExtra(KEY_TRANSITION_VIEW, transitionView?.let { ViewCompat.getTransitionName(it) })

        val options =
            ViewCompat.getTransitionName(transitionView as View)?.let {
            ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, transitionView as View, it) }
        runOnUiThread {
            startActivity(intent, options?.toBundle())
        }
    }


    // Gets called when API Details request got error
    override fun onAPIDetailsReqError() {
        showProgressBar(false)
    }

    // Gets called when user clicks on Map icon from huge list
    override fun onPlaceMapIconClicked(place: Place) {
        val intent = Intent(this@MainActivity, MapActivity::class.java)
        intent.putExtra(KEY_SELECTED_PLACE, place)
        startActivity(intent)
        Log.d(TAG, "Clicked PLACE ICON")
    }

    // Gets called when user clicks on a place name from huge list
    override fun onPlaceNameClicked(place: Place, view: View) {
        showProgressBar(true)
        val placeId: Long = place.properties.id
        fetchPlaceDetails(placeId)
        Log.d(TAG, "Clicked PLACE NAME with ID: $placeId")
        transitionView = view
    }

    // Toggle ProgressBar
    private fun showProgressBar(showIt : Boolean) {
        runOnUiThread {
            if(showIt) {
                progressBar.visibility = View.VISIBLE
                alertDialog.show()

            } else {
                progressBar.visibility = View.GONE
                alertDialog.dismiss()
            }
        }
    }

    // Scroll to top in RecyclerView
    private fun smoothScrollTop() {
        runOnUiThread {
            recyclerView.postDelayed({ recyclerView.smoothScrollToPosition(0) }, 300)
        }
    }

    // Send async get request to provider API (get place details)
    private fun fetchPlaceDetails(placeId : Long) {
        asyncReq = AsyncRequest()
        asyncReq.setParams(false, placeId)
        asyncReq.setListener(this)
        asyncReq.execute()
    }


    // Set PlacesAdapter in RecyclerView in UI Thread
    private fun populateRecyclerView(placesList: MutableList<Place>) {
        runOnUiThread {
            placesAdapter = PlacesAdapter(placesList, this@MainActivity)
            recyclerView.adapter = placesAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onAPIPlacesReqSuccess(placeList: MutableList<Place>) {}
    override fun onAPIPlacesReqError() {}

    // Static referencing
    companion object {
        const val KEY_SELECTED_PLACE: String = "SELECTED_PLACE"
        const val KEY_SELECTED_PLACE_DETAILS: String = "SELECTED_PLACE_DETAILS"
        const val KEY_TRANSITION_VIEW: String = "TRANSITION_VIEW"
        private val TAG = MainActivity::class.java.simpleName
    }
}
