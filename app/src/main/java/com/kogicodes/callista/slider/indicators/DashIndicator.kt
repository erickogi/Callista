package com.kogicodes.callista.slider.indicators

import android.content.Context

import android.widget.LinearLayout

import com.kogicodes.callista.R

import androidx.core.content.res.ResourcesCompat


class DashIndicator(context: Context, indicatorSize: Int, mustAnimateChanges: Boolean) : IndicatorShape(context, indicatorSize, mustAnimateChanges) {


    init {
        setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.indicator_dash_unselected, null))
        val layoutParams = layoutParams as LinearLayout.LayoutParams
        if (layoutParams != null) {
            layoutParams.width = resources.getDimensionPixelSize(R.dimen.default_dash_indicator_width)
            setLayoutParams(layoutParams)
        }
    }

    override fun onCheckedChange(isChecked: Boolean) {
        super.onCheckedChange(isChecked)
        if (isChecked) {
            setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.indicator_dash_selected, null))
        } else {
            setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.indicator_dash_unselected, null))
        }

    }
}
