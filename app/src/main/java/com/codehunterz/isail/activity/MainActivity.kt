package com.codehunterz.isail.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.api.AsyncRequest
import com.codehunterz.isail.listener.OnPlacesRequestListener
import com.codehunterz.isail.model.places.Places
import com.codehunterz.isail.view.PlacesAdapter
import com.google.android.material.snackbar.Snackbar


class MainActivity
    : AppCompatActivity(), OnPlacesRequestListener {

    private var recyclerView: RecyclerView? = null
    private var placesAdapter: PlacesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        // Send async get request to provider API
        val asyncReq = AsyncRequest()
        asyncReq.setListener(this)
        asyncReq.execute();

        // Check permissions
    }

    override fun onPlacesRequestSuccess(places : Places) {
        placesAdapter = places.placeList?.let {
            PlacesAdapter(it)
        }
        runOnUiThread {
            recyclerView?.adapter = placesAdapter
            recyclerView?.layoutManager = LinearLayoutManager(this)
        }
        placesAdapter?.itemClick = { place ->

            Log.d(
                TAG,
                "MAP Got place: " + place.getProperties()?.name +
                        " | ID: " + place.getProperties()?.id +
                        " | LatLng: " + place.geometry?.coordinates?.get(0)
                        + "," + place.geometry?.coordinates?.get(1)
            );

            val intent = Intent(this@MainActivity, MapActivity::class.java)
            intent.putExtra(KEY_SELECTED_PLACE, place)
            startActivity(intent)
        }
    }

    override fun onPlacesRequestError() {
        Toast.makeText(this@MainActivity, "Got ERROR ", Toast.LENGTH_LONG).show()
    }

    private fun showSnackMsg(msg : String) {
        val snackBar = Snackbar.make(currentFocus!!, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction("OK") {
            }
        snackBar.show()
    }


    companion object {
        private val TAG = MainActivity::class.java.simpleName
        const val KEY_SELECTED_PLACE: String = "SELECTED_PLACE"
    }

}
