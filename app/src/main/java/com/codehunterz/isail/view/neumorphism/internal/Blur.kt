package com.codehunterz.isail.view.neumorphism.internal

import android.content.Context
import android.graphics.Bitmap

internal fun Bitmap.blurred(context: Context, radius: Int? = null, sampling: Int? = null): Bitmap {
    return Blurr.of(context, this, BlurrFactor()
        .apply {
        this.width = this@blurred.width
        this.height = this@blurred.height
        if (radius != null) {
            this.radius = radius
        }
        if (sampling != null) {
            this.sampling = sampling
        }
    })
}