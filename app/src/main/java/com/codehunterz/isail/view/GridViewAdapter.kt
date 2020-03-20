package com.codehunterz.isail.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.codehunterz.isail.helper.ImageLoader
import com.codehunterz.isail.model.placedetails.Image

class GridViewAdapter (private val context: Context) : BaseAdapter() {

    private var imageUrls: List<Image>? = null

    fun setImageUrlList(imageUrls: List<Image>?) {
        this.imageUrls = imageUrls
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // convert ImageView to SquareImageView (just the view, not the image itself)
        val view = convertView as? SquareImageView ?: SquareImageView(context)

        // Get the image object for the current position.
        val image = getItem(position)

        // Use builder to Download the image URL and load it into SquareImageView view
        // TY SquareUp group for such a fine library :-)
        ImageLoader.get().load(image.servingUrl).fit().tag(context).into(view)
        return view
    }

    override fun getCount(): Int = imageUrls?.size!!
    override fun getItem(position: Int): Image = this.imageUrls?.get(position)!!
    override fun getItemId(position: Int): Long = position.toLong()
}