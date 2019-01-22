package com.kogicodes.callista.slider.indicators

import android.content.Context
import android.os.Build

import com.kogicodes.callista.R

import androidx.core.content.res.ResourcesCompat


class SquareIndicator(context: Context, indicatorSize: Int, mustAnimateChanges: Boolean) : IndicatorShape(context, indicatorSize, mustAnimateChanges) {

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = ResourcesCompat.getDrawable(resources, R.drawable.indicator_square_unselected, null)
        } else {
            setBackgroundDrawable(resources.getDrawable(R.drawable.indicator_square_unselected))
        }
    }

    override fun onCheckedChange(isChecked: Boolean) {
        super.onCheckedChange(isChecked)
        if (isChecked) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = ResourcesCompat.getDrawable(resources, R.drawable.indicator_square_selected, null)
            } else {
                setBackgroundDrawable(resources.getDrawable(R.drawable.indicator_square_selected))
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                background = ResourcesCompat.getDrawable(resources, R.drawable.indicator_square_unselected, null)
            } else {
                setBackgroundDrawable(resources.getDrawable(R.drawable.indicator_square_unselected))
            }
        }
    }
}
