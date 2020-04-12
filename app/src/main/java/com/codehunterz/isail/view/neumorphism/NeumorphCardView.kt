package com.codehunterz.isail.view.neumorphism

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.graphics.withClip
import com.codehunterz.isail.R
import com.codehunterz.isail.view.neumorphism.internal.blurred
import com.codehunterz.isail.view.neumorphism.internal.onCanvas
import com.codehunterz.isail.view.neumorphism.internal.withClipOut
import com.codehunterz.isail.view.neumorphism.internal.withTranslation

@SuppressLint("NewApi")
class NeumorphCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.defaultNeumorphCardView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    @ShapeType
    private val shapeType: Int
    private val shadowElevation: Int
    private val shadowColorLight: Int
    private val shadowColorDark: Int
    private val shapeAppearanceModel: NeumorphShapeAppearanceModel

    private var lastShadowCache: Bitmap? = null
    private val lightShadowDrawable: GradientDrawable
    private val darkShadowDrawable: GradientDrawable

    private val outlinePath = Path()

    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.NeumorphCardView, defStyleAttr, defStyleRes
        )
        shapeType = a.getInt(R.styleable.NeumorphCardView_neumorph_shapeType, ShapeType.FLAT)
        shadowElevation = a.getDimensionPixelSize(
            R.styleable.NeumorphCardView_neumorph_shadowElevation, 0
        )
        shadowColorLight = a.getColor(
            R.styleable.NeumorphCardView_neumorph_shadowColorLight, Color.WHITE
        )
        shadowColorDark = a.getColor(
            R.styleable.NeumorphCardView_neumorph_shadowColorDark, Color.BLACK
        )
        a.recycle()

        shapeAppearanceModel = NeumorphShapeAppearanceModel
            .builder(context, attrs, defStyleAttr, defStyleRes)
            .build()

        when (shapeType) {
            ShapeType.PRESSED -> {
                lightShadowDrawable = GradientDrawable().apply {
                    setSize(measuredWidth + shadowElevation, measuredHeight + shadowElevation)
                    setStroke(shadowElevation, shadowColorLight)
                    cornerRadii = shapeAppearanceModel.getCornerSize().let {
                        floatArrayOf(0f, 0f, 0f, 0f, it, it, 0f, 0f)
                    }
                }
                darkShadowDrawable = GradientDrawable().apply {
                    setSize(measuredWidth + shadowElevation, measuredHeight + shadowElevation)
                    setStroke(shadowElevation, shadowColorDark)
                    cornerRadii = shapeAppearanceModel.getCornerSize().let {
                        floatArrayOf(it, it, 0f, 0f, 0f, 0f, 0f, 0f)
                    }
                }
            }
            // ShapeType.FLAT
            else -> {
                lightShadowDrawable = GradientDrawable().apply {
                    setSize(measuredWidth, measuredHeight)
                    cornerRadii = shapeAppearanceModel.getCornerSize().let {
                        floatArrayOf(it, it, it, it, it, it, it, it)
                    }
                    setColor(shadowColorLight)
                }
                darkShadowDrawable = GradientDrawable().apply {
                    setSize(measuredWidth, measuredHeight)
                    cornerRadii = shapeAppearanceModel.getCornerSize().let {
                        floatArrayOf(it, it, it, it, it, it, it, it)
                    }
                    setColor(shadowColorDark)
                }
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val cornerSize = shapeAppearanceModel.getCornerSize()
        outlinePath.apply {
            reset()
            addRoundRect(
                0f, 0f, w.toFloat(), h.toFloat(),
                cornerSize, cornerSize,
                Path.Direction.CW
            )
            close()
        }
        when (shapeType) {
            ShapeType.PRESSED -> {
                val width = w + shadowElevation
                val height = h + shadowElevation
                lightShadowDrawable.setSize(width, height)
                lightShadowDrawable.setBounds(0, 0, width, height)
                darkShadowDrawable.setSize(width, height)
                darkShadowDrawable.setBounds(0, 0, width, height)
                lastShadowCache = generateBitmapShadowCache(w, h)
            }
            // ShapeType.FLAT
            else -> {
                lightShadowDrawable.setSize(w, h)
                lightShadowDrawable.setBounds(0, 0, w, h)
                darkShadowDrawable.setSize(w, h)
                darkShadowDrawable.setBounds(0, 0, w, h)
                lastShadowCache = generateBitmapShadowCache(w, h)
            }
        }
    }

    override fun draw(canvas: Canvas) {
        when (shapeType) {
            ShapeType.PRESSED -> {
                canvas.withClip(outlinePath) {
                    lastShadowCache?.let {
                        canvas.drawBitmap(it, 0f, 0f, null)
                    }
                }
            }
            // ShapeType.FLAT
            else -> {
                canvas.withClipOut(outlinePath) {
                    lastShadowCache?.let {
                        val offset = (shadowElevation * 2).toFloat().unaryMinus()
                        canvas.drawBitmap(it, offset, offset, null)

                    }
                }
            }
        }
        canvas.withClip(outlinePath) {
            super.draw(this)
        }
    }

    private fun generateBitmapShadowCache(w: Int, h: Int): Bitmap {
        val shadowElevationF = shadowElevation.toFloat()
        when (shapeType) {
            ShapeType.PRESSED -> {
                return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    .onCanvas {
                        withTranslation(-shadowElevationF, -shadowElevationF) {
                            lightShadowDrawable.draw(this)
                        }
                        darkShadowDrawable.draw(this)
                    }
                    .blurred(context, radius = 25, sampling = 2)
            }
            else -> {
                val width: Int = w + shadowElevation * 4
                val height: Int = h + shadowElevation * 4
                return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    .onCanvas {
                        withTranslation(shadowElevationF, shadowElevationF) {
                            lightShadowDrawable.draw(this)
                        }
                        withTranslation(shadowElevationF * 3, shadowElevationF * 3) {
                            darkShadowDrawable.draw(this)
                        }
                    }
                    .blurred(context)
            }
        }
    }
}