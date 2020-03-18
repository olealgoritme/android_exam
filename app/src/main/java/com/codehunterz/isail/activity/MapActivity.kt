package com.codehunterz.isail.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codehunterz.isail.R
import com.codehunterz.isail.model.IconDetails
import com.codehunterz.isail.model.places.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var mCameraPosition: CameraPosition? = null
    private var mPlacesClient: PlacesClient? = null
    private var mLocationPermissionGranted= false

    // Received Place object from different Activity through intent
    private var mPlace: Place? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve camera position from saved instance state.
        if (savedInstanceState != null) mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)

        // Get Place object from intent
        mPlace = intent.getSerializableExtra(MainActivity.KEY_SELECTED_PLACE) as? Place
        setContentView(R.layout.activity_maps)

        // Construct a PlacesClient
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        mPlacesClient = Places.createClient(this)

        // Build the map.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap!!.cameraPosition)
            super.onSaveInstanceState(outState)
        }
    }


    override fun onMapReady(map: GoogleMap) {
        mMap = map;
        mMap!!.setMaxZoomPreference(DEFAULT_ZOOM.toFloat())

        // Prompt the user for permission.
        getLocationPermission()

        if(mPlace != null) {
            val location: LatLng? = mPlace!!.geometry?.coordinates?.get(1)?.let { mPlace!!.geometry?.coordinates?.get(0)?.let { it1 -> LatLng(it1, it) } }
            val markerOptions = location?.let { MarkerOptions().title(mPlace!!.getProperties()?.name).position(it)}?.snippet(mPlace!!.getProperties()?.icon?.let { IconDetails.getDetails(it)})
            val marker= mMap?.addMarker(markerOptions)
            marker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            mMap?.moveCamera(CameraUpdateFactory.newLatLng(location))
            mMap?.setOnMapLoadedCallback {
                mMap?.animateCamera(CameraUpdateFactory.zoomOut(), 1000, null)
                mMap?.animateCamera(CameraUpdateFactory.zoomTo(5.0f), 1500, null)
            }
            marker?.showInfoWindow()
            Log.d(TAG, "Location object: " + location.toString())
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,  res: IntArray) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (res.isNotEmpty() && res[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true
                }
            }
        }
    }

    companion object {
        private val TAG = MapActivity::class.java.simpleName
        private const val DEFAULT_ZOOM: Int = 10
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val KEY_CAMERA_POSITION = "camera_position"
    }
}