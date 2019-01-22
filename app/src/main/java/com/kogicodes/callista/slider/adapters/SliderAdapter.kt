package com.kogicodes.callista.slider.adapters


import com.kogicodes.callista.slider.SlideType
import com.kogicodes.callista.slider.viewholder.ImageSlideViewHolder

abstract class SliderAdapter {
    abstract val itemCount: Int

    fun getSlideType(position: Int): SlideType {
        return SlideType.IMAGE
    }

    abstract fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder)
}
