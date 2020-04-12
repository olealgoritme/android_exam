package com.codehunterz.isail.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codehunterz.isail.R
import com.codehunterz.isail.helper.IconDetails
import com.codehunterz.isail.model.placedetails.PlaceDetails
import com.codehunterz.isail.model.places.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mLocationPermissionGranted = false
    private lateinit var mMap: GoogleMap
    private lateinit var mCameraPosition: CameraPosition
    private var mPlace: Place? = null
    private var mPlaceDetails: PlaceDetails? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve camera position from saved instance state.
        if (savedInstanceState != null) mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)!!

        // Get Place object from intent
        mPlace = (intent.getParcelableExtra(MainActivity.KEY_SELECTED_PLACE) as? Place)
        mPlaceDetails = (intent.getParcelableExtra(MainActivity.KEY_SELECTED_PLACE_DETAILS) as? PlaceDetails)

        setContentView(R.layout.activity_maps)

       supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Get the manager!
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
       outState.putParcelable(KEY_CAMERA_POSITION, mMap.cameraPosition)
       super.onSaveInstanceState(outState)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.setMaxZoomPreference(DEFAULT_ZOOM.toFloat())

        // Prompt the user for permission.
        getLocationPermission()

        if(mPlace != null)
            setMapPlacesInfo()

        if(mPlaceDetails != null)
            setMapPlaceDetailsInfo()
    }

    private fun animateCamera(location: LatLng, mMap : GoogleMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap.setOnMapLoadedCallback {
            mMap.animateCamera(CameraUpdateFactory.zoomOut(), 1500, null)
            mMap.animateCamera(CameraUpdateFactory.zoomTo(5.0f), 2000, null)
        }
    }

    private fun setMapPlaceDetailsInfo() {
        val location = LatLng(mPlaceDetails!!.lat, mPlaceDetails!!.lon)

        val markerOptions =
            MarkerOptions()
                .title(mPlaceDetails?.name)
                .position(location)

        val marker= mMap.addMarker(markerOptions)
        marker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        animateCamera(location, mMap)
        marker?.showInfoWindow()
    }

    private fun setMapPlacesInfo() {
        if(mPlace == null) return
            val location = LatLng(
                mPlace!!.getLat(),
                mPlace!!.getLng())
            val markerOptions =
                MarkerOptions()
                        .title(mPlace?.properties?.name)
                        .position(location)
                        .snippet(mPlace!!.getIcon().let { IconDetails.getDetails(it) })
            val marker= mMap.addMarker(markerOptions)
            marker?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            animateCamera(location, mMap)
            marker?.showInfoWindow()
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
        private const val DEFAULT_ZOOM: Int = 10
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val KEY_CAMERA_POSITION = "camera_position"
    }
}