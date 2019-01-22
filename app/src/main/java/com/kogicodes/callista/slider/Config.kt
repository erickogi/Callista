package com.kogicodes.callista.slider

import android.content.Context
import android.graphics.drawable.Drawable

import com.kogicodes.callista.R

import androidx.core.content.ContextCompat


class Config private constructor() {
    var hideIndicators = false
    var loopSlides = true
    var indicatorSize = NOT_SELECTED
    var selectedSlideIndicator: Drawable? = null
    var unselectedSlideIndicator: Drawable? = null
    var animateIndicators = true
    var slideChangeInterval = 0
    var emptyView = NOT_SELECTED

    class Builder(context: Context) {
        private val config = Config()
        private val context: Context

        init {
            this.context = context.applicationContext
        }

        fun hideIndicators(hideIndicators: Boolean): Builder {
            config.hideIndicators = hideIndicators
            return this
        }

        fun loopSlides(loopSlides: Boolean): Builder {
            config.loopSlides = loopSlides
            return this
        }

        fun indicatorSize(indicatorSize: Int): Builder {
            config.indicatorSize = indicatorSize
            return this
        }

        fun selectedSlideIndicator(selectedSlideIndicator: Drawable): Builder {
            config.selectedSlideIndicator = selectedSlideIndicator
            return this
        }

        fun unselectedSlideIndicator(unselectedSlideIndicator: Drawable): Builder {
            config.unselectedSlideIndicator = unselectedSlideIndicator
            return this
        }

        fun animateIndicators(animateIndicators: Boolean): Builder {
            config.animateIndicators = animateIndicators
            return this
        }

        fun slideChangeInterval(slideChangeInterval: Int): Builder {
            config.slideChangeInterval = slideChangeInterval
            return this
        }

        fun emptyView(emptyView: Int): Builder {
            config.emptyView = emptyView
            return this
        }


        fun build(): Config {
            if (config.selectedSlideIndicator == null) {
                config.selectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_circle_selected)
            }

            if (config.unselectedSlideIndicator == null) {
                config.unselectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_circle_unselected)
            }

            if (config.indicatorSize == NOT_SELECTED) {
                config.indicatorSize = context.resources.getDimensionPixelSize(R.dimen.default_indicator_size)
            }

            return config
        }
    }

    companion object {
        val NOT_SELECTED = -1
    }


}
