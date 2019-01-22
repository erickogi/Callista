package com.kogicodes.callista.slider.adapters

import android.util.Log


class PositionController(private val sliderAdapter: SliderAdapter, private var loop: Boolean) {
    private var recyclerViewAdapter: SliderRecyclerViewAdapter? = null

    val lastUserSlidePosition: Int
        get() = sliderAdapter.itemCount - 1

    val firstUserSlidePosition: Int
        get() = 0

    fun getUserSlidePosition(position: Int): Int {
        return if (loop) {
            if (position == 0) {
                recyclerViewAdapter!!.itemCount - 3
            } else if (position == recyclerViewAdapter!!.itemCount - 1) {
                0
            } else {
                position - 1
            }
        } else {
            position
        }
    }

    fun getRealSlidePosition(position: Int): Int {
        if (!loop) {
            return position
        } else {
            if (position >= 0 && position < sliderAdapter.itemCount) {
                return position + 1
            } else {
                Log.e(TAG, "setSelectedSlide: Invalid Item Position")
                return 1
            }
        }
    }

    fun setRecyclerViewAdapter(recyclerViewAdapter: SliderRecyclerViewAdapter) {
        this.recyclerViewAdapter = recyclerViewAdapter
    }

    fun getNextSlide(currentPosition: Int): Int {
        return if (currentPosition < recyclerViewAdapter!!.itemCount - 1) {
            currentPosition + 1
        } else {
            if (loop) 1 else 0
        }
    }

    fun setLoop(loop: Boolean) {
        this.loop = loop
    }

    companion object {
        private val TAG = "PositionController"
    }
}
