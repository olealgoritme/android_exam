package com.codehunterz.isail.view.neumorphism

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.codehunterz.isail.R
import com.codehunterz.isail.view.neumorphism.internal.blurred
import com.codehunterz.isail.view.neumorphism.internal.withClip
import com.codehunterz.isail.view.neumorphism.internal.withClipOut
import com.codehunterz.isail.view.neumorphism.internal.withTranslation

class NeumorphFab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.defaultNeumorphFloatingActionButton
) : AppCompatImageButton(context, attrs, defStyleAttr) {

    private val shadowElevation: Int
    private val shadowColorLight: Int
    private val shadowColorDark: Int

    private var lastShadowCache: Bitmap? = null
    private val lightShadowDrawable: GradientDrawable
    private val darkShadowDrawable: GradientDrawable

    private val shapeAppearanceModel: NeumorphShapeAppearanceModel
    private val outlinePath = Path()

    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.NeumorphFAB, defStyleAttr, defStyleRes
        )
        shadowElevation = a.getDimensionPixelSize(
            R.styleable.NeumorphFAB_neumorph_shadowElevation, 0
        )
        shadowColorLight = a.getColor(
            R.styleable.NeumorphFAB_neumorph_shadowColorLight, Color.WHITE
        )
        shadowColorDark = a.getColor(
            R.styleable.NeumorphFAB_neumorph_shadowColorDark, Color.BLACK
        )
        a.recycle()

        shapeAppearanceModel = NeumorphShapeAppearanceModel
            .builder(context, attrs, defStyleAttr, defStyleRes)
            .build()

        lightShadowDrawable = GradientDrawable().apply {
            setSize(measuredWidth, measuredHeight)
            shape = GradientDrawable.OVAL
            setColor(shadowColorLight)
        }
        darkShadowDrawable = GradientDrawable().apply {
            setSize(measuredWidth, measuredHeight)
            shape = GradientDrawable.OVAL
            setColor(shadowColorDark)
        }
    }

    @SuppressLint("NewApi")
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        outlinePath.apply {
            reset()
            addOval(0f, 0f, w.toFloat(), h.toFloat(), Path.Direction.CW)
            close()
        }
        lightShadowDrawable.setSize(w, h)
        lightShadowDrawable.setBounds(0, 0, w, h)
        darkShadowDrawable.setSize(w, h)
        darkShadowDrawable.setBounds(0, 0, w, h)
        lastShadowCache = generateBitmapShadowCache(w, h)
    }

    override fun draw(canvas: Canvas) {
        canvas.withClipOut(outlinePath) {
            lastShadowCache?.let {
                val offset = (shadowElevation * 2).toFloat().unaryMinus()
                canvas.drawBitmap(it, offset, offset, null)
            }
        }
        canvas.withClip(outlinePath) {
            super.draw(canvas)
        }
    }

    private fun generateBitmapShadowCache(w: Int, h: Int): Bitmap? {
        val width: Int = w + shadowElevation * 4
        val height: Int = h + shadowElevation * 4
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.withTranslation(shadowElevation.toFloat(), shadowElevation.toFloat()) {
            lightShadowDrawable.draw(this)
        }
        canvas.withTranslation(shadowElevation.toFloat() * 3, shadowElevation.toFloat() * 3) {
            darkShadowDrawable.draw(this)
        }
        return bitmap.blurred(context)
    }
}