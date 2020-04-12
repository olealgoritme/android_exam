package com.codehunterz.isail.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.codehunterz.isail.R
import com.codehunterz.isail.helper.ImageLoader
import com.codehunterz.isail.listener.OnPlaceImageClickListener
import com.codehunterz.isail.model.placedetails.PlaceDetails

class PlaceImageAdapter (private var imageList: List<PlaceDetails.Image>, private var listener : OnPlaceImageClickListener)
    : RecyclerView.Adapter<PlaceImageAdapter.PlaceImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaceImageViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PlaceImageViewHolder, position: Int) {
        val img = imageList[position]
        holder.bind(img)
    }

    override fun getItemCount(): Int = imageList.size

    inner class PlaceImageViewHolder (inflater: LayoutInflater, parent: ViewGroup)
        :RecyclerView.ViewHolder(inflater.inflate(R.layout.list_image_item, parent, false)) {

        private var textView: TextView = itemView.findViewById(R.id.caption)
        private var imageView: ImageView = itemView.findViewById(R.id.image)

        init {
            // Place Image click Listener
            imageView.setOnClickListener { listener.onPlaceImageClicked(imageView) }
        }

        fun bind(image: PlaceDetails.Image) {
            // Set caption in view
            if(image.caption.isNotEmpty())
                textView.text = image.caption

            // Set image in view
            ImageLoader
                .get(itemView.context)
                .load(image.servingUrl)
                .resize(3000, 2000)
                .centerCrop()
                .tag(image.servingUrl)
                .into(imageView)
        }
    }
}
