package com.codehunterz.isail.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.SettingsSlicesContract.KEY_LOCATION
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codehunterz.isail.R
import com.codehunterz.isail.model.places.Place
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var mCameraPosition: CameraPosition? = null

    // The entry point to the Places API.
    private var mPlacesClient: PlacesClient? = null

    // The entry point to the Fused Location Provider.
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val mDefaultLocation = LatLng(3.127146811488748,39.840974684086724)
    private var mLocationPermissionGranted = false
    private var mLastKnownLocation: Location? = null

    // Received LatLng from different Activity
    private var mPlace: Place? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation =
                savedInstanceState.getParcelable(KEY_LOCATION)
            mCameraPosition =
                savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        // Get Place object from intent
        mPlace = intent.getSerializableExtra(MainActivity.KEY_SELECTED_PLACE) as? Place
        setContentView(R.layout.activity_maps)

        // Construct a PlacesClient
        Places.initialize(
            applicationContext,
            getString(R.string.google_maps_key)
        )
        mPlacesClient = Places.createClient(this)

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Build the map.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (mMap != null) {
            outState.putParcelable(
                KEY_CAMERA_POSITION,
                mMap!!.cameraPosition
            )
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation)
            super.onSaveInstanceState(outState)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map;
        mMap!!.setMinZoomPreference(DEFAULT_ZOOM.toFloat())

        Log.d(TAG, "MAP IS READY!")
        // Prompt the user for permission.
        getLocationPermission()

        if(mPlace != null) {
            val location: LatLng? = mPlace!!.geometry?.coordinates?.get(1)?.let { mPlace!!.geometry?.coordinates?.get(0)?.let { it1 -> LatLng(it1, it) } }
            Log.d(TAG, "Location object: " + location.toString())
            mMap?.addMarker(location?.let { MarkerOptions().title(mPlace!!.getProperties()?.name).position(it)})
            mMap?.animateCamera(CameraUpdateFactory.newLatLng(location))
            mMap?
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
                // If request is cancelled, the result arrays are empty.
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
        private const val KEY_LOCATION = "location"
    }
}