package com.codehunterz.isail.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.codehunterz.isail.R
import com.codehunterz.isail.activity.MainActivity.Companion.KEY_SELECTED_PLACE
import com.codehunterz.isail.activity.MainActivity.Companion.KEY_SELECTED_PLACE_DETAILS
import com.codehunterz.isail.helper.ImageLoader
import com.codehunterz.isail.model.placedetails.Image
import com.codehunterz.isail.model.placedetails.PlaceDetails
import com.codehunterz.isail.view.GridViewAdapter
import com.codehunterz.isail.view.ScrollListener

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Get PlaceDetails object from intent
        val mPlaceDetails = intent.getSerializableExtra(MainActivity.KEY_SELECTED_PLACE_DETAILS) as? PlaceDetails
        Log.d("PlaceDetails", "PlaceDetails: " + mPlaceDetails.toString())

        if(mPlaceDetails == null) return;

        // Set Place Name
        if(mPlaceDetails.name!!.isNotEmpty()) setPlaceName(mPlaceDetails.name!!)

        // Set Banner Image
        if(mPlaceDetails.banner!!.isNotEmpty()) setBannerImage(mPlaceDetails.banner!!)

        // Set Map Icon click listener
        if(mPlaceDetails.lat != null && mPlaceDetails.lon != null) setMapIconImageClickListener(mPlaceDetails)

        // Set Description Text
        if(mPlaceDetails.comments!!.isNotEmpty()) setDescription(mPlaceDetails.comments!!)

        // Set Grid with Images
        if(mPlaceDetails.images!!.isNotEmpty()) setGridWithImages(mPlaceDetails.images!!)

    }

    private fun setMapIconImageClickListener(placeDetails : PlaceDetails) {
       val mIconImage: ImageView = this.findViewById(R.id.details_image_map_icon)
       mIconImage.setOnClickListener {
           val intent = Intent(this@DetailsActivity, MapActivity::class.java)
           intent.putExtra(KEY_SELECTED_PLACE_DETAILS, placeDetails)
           startActivity(intent)
       }
    }

    private fun setPlaceName(name : String) {
        val mPlaceName: TextView = this.findViewById(R.id.details_text_place_name)
        mPlaceName.text = name
    }

    private fun setBannerImage(bannerUrl : String) {
        val mBannerImage: ImageView = this.findViewById(R.id.details_image_banner)
        ImageLoader
            .get()
            .load(bannerUrl)
            .fit()
            .tag(this@DetailsActivity)
            .into(mBannerImage)
    }

    private fun setDescription(description: String) {
        val mDescriptionText: TextView = this.findViewById(R.id.details_description_text)
        mDescriptionText.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun setGridWithImages(imgUrlList : List<Image>) {
            // Set list in adapter
            val mGridViewAdapter = GridViewAdapter(this@DetailsActivity)
            mGridViewAdapter.setImageUrlList(imgUrlList)

            // Connect scroll listener and adapter to GridView
            this.findViewById<GridView>(R.id.grid_view).apply {
                adapter = mGridViewAdapter
                setOnScrollListener(ScrollListener(this@DetailsActivity))
        }
    }
}