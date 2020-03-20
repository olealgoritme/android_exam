package com.codehunterz.isail.helper

import com.squareup.picasso.Picasso

object ImageLoader {

    private val instance: Picasso by lazy {
        Picasso
            .Builder(ImageProvider.autoContext!!)
            .build()
    }

    @JvmStatic
    fun get() = instance
}