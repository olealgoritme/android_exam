package com.codehunterz.isail.helper

import android.content.Context
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso

object ImageLoader {

    private var ctx: Context? = null

    private val instance: Picasso by lazy {
        Picasso
            .Builder(ctx!!)
            .memoryCache(LruCache(250000000)) // 250 MB of image cache
            .build()
    }

    @JvmStatic
    fun get(ctx: Context): Picasso {
         this.ctx = ctx;
        return instance
    }
}