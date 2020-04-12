package com.codehunterz.isail.view.neumorphism.internal

import android.graphics.Canvas
import android.graphics.Path
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
internal object CanvasCompat {

    fun clipOutPath(canvas: Canvas, path: Path): Boolean {
        return canvas.clipOutPath(path)
    }
}