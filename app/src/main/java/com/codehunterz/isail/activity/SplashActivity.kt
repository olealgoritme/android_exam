package com.codehunterz.isail.activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.codehunterz.isail.R
import com.codehunterz.isail.api.AsyncRequest
import com.codehunterz.isail.db.PlaceDao
import com.codehunterz.isail.helper.Snacky
import com.codehunterz.isail.listener.OnAPIRequestListener
import com.codehunterz.isail.listener.OnSnackBarClickedListener
import com.codehunterz.isail.model.placedetails.PlaceDetails
import com.codehunterz.isail.model.places.Place
import kotlinx.android.synthetic.main.activity_splash.*

@RequiresApi(Build.VERSION_CODES.M)
class SplashActivity : AppCompatActivity(), OnAPIRequestListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set dim amount (bright if its dim)
        window.attributes.dimAmount = 0.0f

        setContentView(R.layout.activity_splash)

        // Network sanity check
        if(!isNetworkConnected()) {
            Handler().postDelayed({
                Snacky().apply {
                    with    { this@SplashActivity}
                    msg     { "ERROR When connecting to the Internet! Press OK to restart app." }
                    clicked { object: OnSnackBarClickedListener {
                        override fun onButtonClick() {
                            runOnUiThread {
                                finish()
                                startActivity(Intent(this@SplashActivity, SplashActivity::class.java))
                            }
                        } } } }
                    .build().show()
            }, 2000)
        } else {
            fetchPlaces()
        }
    }

    private fun startMainWithAnimationOut(delay: Long) {
            Handler().postDelayed({
                val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f, 0f)
                val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f, 0f)
                val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1.0f, 0f)

                runOnUiThread {
                    ObjectAnimator.ofPropertyValuesHolder(constrainLayout, scaleX, scaleY, alpha).apply {
                        duration = 1000
                        start()
                    }.doOnEnd {
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }, delay)
    }


    // Fetching places in Thread (fire and forget Thread)
    private fun fetchPlaces() {
        // Check if we already have places in our database
        Thread(Runnable {
            val placeList:MutableList<Place> = PlaceDao().fetchAll()
            // Send Async API request if list from DB is empty
            if(placeList.isEmpty()) {
                // Send async get request to provider API (get ALL places)
                val asyncReq = AsyncRequest()
                asyncReq.setParams(true)
                asyncReq.setListener(this)
                asyncReq.execute()
            } else {
                runOnUiThread {
                    // Hide loading animation
                    loading.visibility = View.GONE
                    startMainWithAnimationOut(2000)
                }
            }
        }).run()
    }

    // Inserting all places into DB (fire and forget Thread)
    private fun insertPlacesToDb(placeList: MutableList<Place>) {
        Thread().run {
            PlaceDao().insertList(placeList)
        }
    }

    override fun onAPIPlacesReqSuccess(placeList: MutableList<Place>) {
        insertPlacesToDb(placeList)
        startMainWithAnimationOut(8000)
    }

    override fun onAPIPlacesReqError() {
        Snacky().apply {
            with { this@SplashActivity }
            msg { "ERROR when Requesting Places from API. Please contact developer" }
        }.build().show()
    }

    private fun isNetworkConnected(): Boolean {
        val cm =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val n: Network? = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                return (nc != null && nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc != null && nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ))
        }
        return false
    }

    override fun onAPIDetailsReqSuccess(placeDetails: PlaceDetails) {}
    override fun onAPIDetailsReqError() {}

}
