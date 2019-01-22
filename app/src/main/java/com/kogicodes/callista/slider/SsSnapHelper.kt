package com.kogicodes.callista.slider

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

internal class SsSnapHelper(private val onSelectedItemChange: OnSelectedItemChange) : PagerSnapHelper() {
    private var lastPosition: Int = 0

    override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
        val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
        if (position != RecyclerView.NO_POSITION && lastPosition != position && position < layoutManager.itemCount) {
            onSelectedItemChange.onSelectedItemChange(position)
            lastPosition = position
        }
        return position
    }

    interface OnSelectedItemChange {
        fun onSelectedItemChange(position: Int)
    }
}
