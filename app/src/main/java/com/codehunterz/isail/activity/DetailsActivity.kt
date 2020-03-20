package com.codehunterz.isail.activity

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.codehunterz.isail.R
import com.codehunterz.isail.helper.ImageLoader
import com.codehunterz.isail.model.placedetails.Image
import com.codehunterz.isail.model.placedetails.PlaceDetails
import com.codehunterz.isail.view.GridViewAdapter
import com.codehunterz.isail.view.ScrollListener

class DetailsActivity : AppCompatActivity() {

    // View references
    private var mGridViewAdapter: GridViewAdapter? = null
    private var mPlaceName: TextView? = null
    private var mBannerImage: ImageView? = null
    private var mDescriptionText: TextView? = null

    // PlaceDetails object reference
    private var mPlaceObject: PlaceDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Get PlaceDetails object from intent
        mPlaceObject = intent.getSerializableExtra(MainActivity.KEY_SELECTED_PLACE_DETAILS) as? PlaceDetails
        Log.d("PlaceDetails", "PlaceDetails: " + mPlaceObject.toString())

        if(mPlaceObject == null)
            return;

        // Set Place Name
        if(mPlaceObject?.name?.isNotEmpty()!!)
            mPlaceObject!!.name?.let { setPlaceName(it) }

        // Set Banner Image
        if(mPlaceObject?.banner?.isNotEmpty()!!)
            mPlaceObject!!.banner?.let { setBannerImage(it) }

        // Set Description Text
        if(mPlaceObject?.comments?.isNotEmpty()!!)
            mPlaceObject!!.comments?.let { setDescription(it) }

        // Set Grid with Images
        if(mPlaceObject?.images?.isNotEmpty()!!)
            mPlaceObject!!.images?.let { setGridWithImages(it) }

    }

    private fun setPlaceName(name : String) {
        mPlaceName = this.findViewById(R.id.details_text_place_name)
        mPlaceName!!.text = name
    }

    private fun setBannerImage(bannerUrl : String) {
        mBannerImage = this.findViewById(R.id.details_image_banner)
        ImageLoader
            .get()
            .load(bannerUrl)
            .fit()
            .tag(this@DetailsActivity)
            .into(mBannerImage)
    }

    private fun setDescription(description: String) {
        mDescriptionText = this.findViewById(R.id.details_description_text)
        mDescriptionText!!.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun setGridWithImages(imgUrlList : List<Image>) {
            // Set list in adapter
            mGridViewAdapter = GridViewAdapter(this@DetailsActivity)
            mGridViewAdapter!!.setImageUrlList(imgUrlList)

            // Connect scroll listener and adapter to GridView
            findViewById<GridView>(R.id.grid_view).apply {
                adapter = mGridViewAdapter
                setOnScrollListener(ScrollListener(this@DetailsActivity))
        }
    }
}