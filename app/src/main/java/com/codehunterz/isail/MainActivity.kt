package com.codehunterz.isailing

import android.R
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isailing.api.APIClient
import com.codehunterz.isailing.api.APIService
import com.codehunterz.isailing.api.model.places.Places
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), OnPlacesListener {

    private var recyclerView: RecyclerView? = null;
    private var placesAdapter: PlacesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        getPlaces(this)
    }

    private fun getPlaces(placesListener : OnPlacesListener ) {
        val apiClient = APIClient.getIt
        val service = apiClient?.create(APIService::class.java)
        val call = service?.getAllPlaces()

        call?.enqueue(object : Callback<Places> {

            override fun onFailure(call: Call<Places>, t: Throwable) {
                placesListener.onPlacesError();
                Log.e("Main", "Error: " + t.message)
            }

            override fun onResponse(call: Call<Places>, response: Response<Places>) {

                if(response.isSuccessful)
                    response.body()?.let { placesListener.onPlaces(it) }
                else
                    placesListener.onPlacesError()
                    Log.e("Main", "Error! Response: " + response.body())
            }
        });
    }

    override fun onPlaces(places : Places){
        placesAdapter = places.placeList?.let { PlacesAdapter(it) }
        recyclerView?.adapter = placesAdapter
    }

    override fun onPlacesError() {
        Toast.makeText(this@MainActivity, "Got ERROR ", Toast.LENGTH_LONG).show()
    }

}
