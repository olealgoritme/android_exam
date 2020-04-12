package com.codehunterz.isail.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codehunterz.isail.R
import com.codehunterz.isail.activity.MainActivity.Companion.KEY_SELECTED_PLACE_DETAILS
import com.codehunterz.isail.helper.ImageLoader
import com.codehunterz.isail.helper.ImageUtil
import com.codehunterz.isail.listener.OnPlaceImageClickListener
import com.codehunterz.isail.model.placedetails.PlaceDetails
import com.codehunterz.isail.view.PlaceImageAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_details.*
import java.util.*
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
class DetailsActivity : AppCompatActivity(), OnOffsetChangedListener, OnPlaceImageClickListener {

    private lateinit var imageView: ImageView
    private lateinit var imageCountry: ImageView
    private lateinit var imagePlaceType: ImageView
    private lateinit var textDescription: TextView
    private lateinit var placeName: TextView
    private var isHideToolbarView = false

    private lateinit var iconsLayout: FrameLayout
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var colToolbarLayout: CollapsingToolbarLayout

    private var iconsX: Int = 0
    private var iconsY: Int = 0

    private lateinit var mPlaceDetails: PlaceDetails

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""

        // Show ActionBar back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // CollapsingToolbarLayout
        colToolbarLayout = findViewById(R.id.collapsing_toolbar)
        colToolbarLayout.title = ""

        // AppBarLayout
        appBarLayout = findViewById(R.id.appbar)
        appBarLayout.addOnOffsetChangedListener(this)

        // FadeIn Animation
        val animFadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        animFadeIn.duration = 600

        // Get PlaceDetails object from intent
        mPlaceDetails = intent.getParcelableExtra(KEY_SELECTED_PLACE_DETAILS) as PlaceDetails

        // Animation for the icons layout
        iconsLayout = findViewById(R.id.icons_layout)
        iconsLayout.animation = animFadeIn

        // Animation for the backdrop image
        imageView = findViewById(R.id.backdrop)
        imageView.animation =  animFadeIn

        // Place name, description, image of country and place type icon
        placeName = findViewById(R.id.placeName)
        textDescription = findViewById(R.id.card_textView_description_text)
        imageCountry = findViewById(R.id.placeCountryIcon)
        imagePlaceType = findViewById(R.id.placeTypeIcon)

        // Populate view with info from our intent object
        populateViews()

        // Get map icons location position (for animation later)
        val location = IntArray(2)
        iconsLayout.getLocationOnScreen(location)
        iconsX = location[0]
        iconsY = location[1]
    }


    private fun populateViews() {

        // Set Place Name
        if(mPlaceDetails.name.isNotEmpty()) setPlaceName(mPlaceDetails.name)

        // Set Banner Image
        setBannerImage(mPlaceDetails.banner)

        // Set Grid with Images
        if(mPlaceDetails.images.isNotEmpty()) {
            Log.d(TAG, "Images: " + mPlaceDetails.images.size)
            populateRecyclerViewImages(mPlaceDetails.images)
        }

        // Set country icon
        // Find resource id based on string name (country, e.g "no, se, gr")
        val countryResId = resources.getIdentifier(mPlaceDetails.countryCode.toLowerCase(Locale.getDefault()), "drawable", packageName)
        imageCountry.setImageResource(countryResId)

        // Set place type icon
        // Find resource id based on string name (nfl_anchorage, etc)
        var iconName = mPlaceDetails.icon.toLowerCase(Locale.getDefault())
        iconName = iconName.replace("nfl_", "")
            .replace("-26", "")
            .replace(".png", "")
        Log.d(TAG, "Icon name: $iconName")
        val iconResId = resources.getIdentifier(iconName, "drawable", packageName)
        imagePlaceType.setImageResource(iconResId)


        // Set review stars
        if(mPlaceDetails.stars != 0) placeStars.progress = mPlaceDetails.stars * 20

        // Set Map Icon click listener
        if(!mPlaceDetails.lat.isNaN() && !mPlaceDetails.lon.isNaN()) setMapIconImageClickListener(mPlaceDetails)

        // Set Description Text
        if(mPlaceDetails.comments.isNotEmpty()) setDescription(mPlaceDetails.comments)

    }

    private fun setPlaceName(name : String) {
        placeName.text = name
    }

    private fun setDescription(description: String) {
        textDescription.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun setMapIconImageClickListener(placeDetails : PlaceDetails) {
        val iconsLayout: FrameLayout = findViewById(R.id.icons_layout)
        iconsLayout.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra(KEY_SELECTED_PLACE_DETAILS, placeDetails)
            startActivity(intent)
        }
    }

    private fun setBannerImage(bannerUrl : String) {
        if(bannerUrl.isEmpty()) {
            imageView.setImageDrawable(ImageUtil.getGradientDrawable())
        } else {
            ImageLoader
                .get(this)
                .load(bannerUrl)
                .placeholder(ImageUtil.getGradientDrawable())
                .error(ImageUtil.getGradientDrawable())
                .centerCrop()
                .fit()
                .tag(this)
                .into(imageView)
        }
    }

    private fun populateRecyclerViewImages(imgUrlList : List<PlaceDetails.Image>) {
        runOnUiThread {
            val placeImageAdapter = PlaceImageAdapter(imgUrlList, this@DetailsActivity)
            recyclerView.adapter = placeImageAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@DetailsActivity)
        }
    }

    override fun onPlaceImageClicked(imageView: ImageView) {
        Log.d(TAG, "Place Image Clicked")
        runOnUiThread {
            showImage(imageView)
        }
    }

    @SuppressLint("NewApi")
    private fun showImage(imgViewOrig: ImageView) {
        val builder = Dialog(this)
        builder.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val imageView = ImageView(this)
        val bitmap: BitmapDrawable = (imgViewOrig.drawable as BitmapDrawable)
        imageView.setImageDrawable(bitmap)
        builder.addContentView(imageView,
            RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        builder.setOnDismissListener { builder.dismiss() }
        builder.setOnCancelListener { builder.dismiss() }
        imageView.setOnClickListener { builder.dismiss() }
        builder.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()

        if (percentage == 1f && isHideToolbarView) {
            toolbar.title = mPlaceDetails.name

            ObjectAnimator.ofFloat(iconsLayout, View.TRANSLATION_Y, iconsY.toFloat() + 70).apply {
                duration = 100
                start()
            }
            ObjectAnimator.ofFloat(placeName, View.TRANSLATION_Y, -200.0f).apply {
                duration = 150
                start()
            }
            ObjectAnimator.ofFloat(imageCountry, View.TRANSLATION_Y, -200.0f).apply {
                duration = 150
                start()
            }
            ObjectAnimator.ofFloat(imagePlaceType, View.TRANSLATION_Y, -200.0f).apply {
                duration = 150
                start()
            }

            isHideToolbarView = !isHideToolbarView
        } else if ( percentage < 1f && !isHideToolbarView) {
            toolbar.titleMarginBottom = 3000
            ObjectAnimator.ofFloat(iconsLayout, View.TRANSLATION_Y, iconsY.toFloat()).apply {
                duration = 230
                start()
            }
            ObjectAnimator.ofFloat(placeName, View.TRANSLATION_Y, 0f).apply {
                duration = 230
                start()
            }
            ObjectAnimator.ofFloat(imageCountry, View.TRANSLATION_Y, 0f).apply {
                duration = 230
                start()
            }

            ObjectAnimator.ofFloat(imagePlaceType, View.TRANSLATION_Y, 0f).apply {
                duration = 230
                start()
            }
            isHideToolbarView = !isHideToolbarView
        }
    }

    companion object {
        private val TAG = DetailsActivity::class.java.simpleName
    }

}