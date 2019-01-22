package com.kogicodes.callista.slider

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.kogicodes.callista.Application.Companion.context

import com.kogicodes.callista.R
import com.kogicodes.callista.slider.event.OnSlideChangeListener
import com.kogicodes.callista.slider.indicators.CircleIndicator
import com.kogicodes.callista.slider.indicators.DashIndicator
import com.kogicodes.callista.slider.indicators.IndicatorShape
import com.kogicodes.callista.slider.indicators.RoundSquareIndicator
import com.kogicodes.callista.slider.indicators.SquareIndicator

import java.util.ArrayList

class SlideIndicatorsGroup(private val contextx: Context, private val selectedSlideIndicator: Drawable?, private val unselectedSlideIndicator: Drawable?, private val defaultIndicator: Int, private val indicatorSize: Int, mustAnimateIndicators: Boolean) : LinearLayout(contextx), OnSlideChangeListener {
    private var slidesCount: Int = 0
    private var mustAnimateIndicators = true
    private val indicatorShapes = ArrayList<IndicatorShape>()

    init {
        this.mustAnimateIndicators = mustAnimateIndicators
        setup()
    }

    fun setSlides(slidesCount: Int) {
        removeAllViews()
        indicatorShapes.clear()
        this.slidesCount = 0
        for (i in 0 until slidesCount) {
            onSlideAdd()
        }
        this.slidesCount = slidesCount
    }

    fun onSlideAdd() {
        this.slidesCount += 1
        addIndicator()
    }

    private fun addIndicator() {
        val indicatorShape: IndicatorShape
        if (selectedSlideIndicator != null && unselectedSlideIndicator != null) {
            indicatorShape = object : IndicatorShape(contextx, indicatorSize, mustAnimateIndicators) {
                override fun onCheckedChange(isChecked: Boolean) {
                    super.onCheckedChange(isChecked)
                    if (isChecked) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            background = selectedSlideIndicator
                        } else {
                            setBackgroundDrawable(selectedSlideIndicator)
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            background = unselectedSlideIndicator
                        } else {
                            setBackgroundDrawable(unselectedSlideIndicator)
                        }
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                indicatorShape.setBackground(unselectedSlideIndicator)
            } else {
                indicatorShape.setBackgroundDrawable(unselectedSlideIndicator)
            }
            indicatorShapes.add(indicatorShape)
            addView(indicatorShape)

        } else {
            when (defaultIndicator) {
                IndicatorShape.SQUARE -> {
                    indicatorShape = SquareIndicator(contextx, indicatorSize, mustAnimateIndicators)
                    indicatorShapes.add(indicatorShape)
                    addView(indicatorShape)
                }
                IndicatorShape.ROUND_SQUARE -> {
                    indicatorShape = RoundSquareIndicator(contextx, indicatorSize, mustAnimateIndicators)
                    indicatorShapes.add(indicatorShape)
                    addView(indicatorShape)
                }
                IndicatorShape.DASH -> {
                    indicatorShape = DashIndicator(contextx, indicatorSize, mustAnimateIndicators)
                    indicatorShapes.add(indicatorShape)
                    addView(indicatorShape)
                }

                IndicatorShape.CIRCLE -> {
                    indicatorShape = CircleIndicator(contextx, indicatorSize, mustAnimateIndicators)
                    indicatorShapes.add(indicatorShape)
                    addView(indicatorShape)
                }
                else -> {
                }
            }
        }
    }

    fun setup() {
        orientation = LinearLayout.HORIZONTAL
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        val margin = resources.getDimensionPixelSize(R.dimen.default_indicator_margins) * 2
        layoutParams.setMargins(0, 0, 0, margin)
        setLayoutParams(layoutParams)
    }

    override fun onSlideChange(selectedSlidePosition: Int) {
        Log.i(TAG, "onSlideChange: $selectedSlidePosition")
        for (i in indicatorShapes.indices) {
            if (i == selectedSlidePosition) {
                indicatorShapes[i].onCheckedChange(true)
            } else {
                indicatorShapes[i].onCheckedChange(false)
            }
        }
    }

    fun setMustAnimateIndicators(shouldAnimate: Boolean) {
        this.mustAnimateIndicators = shouldAnimate
        for (indicatorShape in indicatorShapes) {
            indicatorShape.setMustAnimateChange(shouldAnimate)
        }
    }

    companion object {
        private val TAG = "SlideIndicatorsGroup"
    }

}
