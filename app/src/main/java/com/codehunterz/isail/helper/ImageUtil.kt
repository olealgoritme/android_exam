package com.codehunterz.isail.helper

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

class ImageUtil {

    companion object {
        private val colorList: List<GradientDrawable>
            get() = listOf(
                GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#ffafbd"), Color.parseColor("#ffc3a0"))
                ), // Roseanna
                GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#2193b0"), Color.parseColor("#6dd5ed"))
                ), // Sexy blue
                GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#cc2b5e"), Color.parseColor("#753a88"))
                ), // Purple love
                GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#ee9ca7"), Color.parseColor("#ffdde1"))
                ), // Piglet
                GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#42275a"), Color.parseColor("#734b6d"))
                ), // Mauve
                GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#bdc3c7"), Color.parseColor("#2c3e50"))
                ), // 50 shades of grey
                GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#de6262"), Color.parseColor("#ffb88c"))
                ), // A lost memory
                GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    intArrayOf(Color.parseColor("#06beb6"), Color.parseColor("#48b1bf"))
                ) // Socialive
            )

        fun getGradientDrawable(): GradientDrawable {
            val drawable = this.colorList.random();
            return drawable
        }

    }
}