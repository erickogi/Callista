package com.kogicodes.callista.slider.indicators

import android.content.Context
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout

import com.kogicodes.callista.R


abstract class IndicatorShape(context: Context, private var indicatorSize: Int, private var mustAnimateChange: Boolean) : ImageView(context) {
    private var isChecked = false

    init {
        setup()
    }


    private fun setup() {
        if (this.indicatorSize == 0) {
            indicatorSize = resources.getDimension(R.dimen.default_indicator_size).toInt()
        }
        val layoutParams = LinearLayout.LayoutParams(indicatorSize, indicatorSize)
        val margin = resources.getDimensionPixelSize(R.dimen.default_indicator_margins)
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        layoutParams.setMargins(margin, 0, margin, 0)
        setLayoutParams(layoutParams)
    }

    open fun onCheckedChange(isChecked: Boolean) {
        if (this.isChecked != isChecked) {
            if (mustAnimateChange) {
                if (isChecked) {
                    scale()
                } else {
                    descale()
                }
            } else {
                if (isChecked) {
                    scale(0)
                } else {
                    descale(0)
                }
            }
            this.isChecked = isChecked
        }
    }

    private fun scale(duration: Int = ANIMATION_DURATION) {
        val scaleAnimation = ScaleAnimation(1f, 1.1f, 1f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = duration.toLong()
        scaleAnimation.fillAfter = true
        startAnimation(scaleAnimation)
    }

    private fun descale(duration: Int = ANIMATION_DURATION) {
        val scaleAnimation = ScaleAnimation(1.1f, 1f, 1.1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = duration.toLong()
        scaleAnimation.fillAfter = true
        startAnimation(scaleAnimation)
    }

    fun setMustAnimateChange(mustAnimateChange: Boolean) {
        this.mustAnimateChange = mustAnimateChange
    }

    companion object {

        val CIRCLE = 0
        val SQUARE = 1
        val ROUND_SQUARE = 2
        val DASH = 3

        private val ANIMATION_DURATION = 150
    }


}
