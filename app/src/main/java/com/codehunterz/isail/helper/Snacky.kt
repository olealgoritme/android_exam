package com.codehunterz.isail.helper

import android.app.Activity
import android.view.View
import com.codehunterz.isail.listener.OnSnackBarClickedListener
import com.google.android.material.snackbar.Snackbar

// Just a mini helper class to test out kotlin lambda builder pattern
class Snacky() {

    private lateinit var listener: OnSnackBarClickedListener
    private lateinit var msg : String
    private lateinit var snacky : Snackbar
    private lateinit var activity: Activity

    fun with(lambda: () -> Activity) {
        this.activity = lambda()
    }

    fun msg(lambda: () -> String) {
        this.msg = lambda()
    }

    fun clicked(lambda: () -> OnSnackBarClickedListener) {
        this.listener = lambda()
    }

    fun build() : Snackbar {
        val view: View = activity.findViewById(android.R.id.content)
        snacky = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction("OK") {
                listener.onButtonClick()
            }
        return snacky
    }
}

