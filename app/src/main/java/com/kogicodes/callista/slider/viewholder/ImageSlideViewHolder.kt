package com.kogicodes.callista.slider.viewholder


import android.view.View
import android.widget.ImageView

import com.kogicodes.callista.slider.Slider

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView


class ImageSlideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageView: ImageView

    init {
        this.imageView = itemView as ImageView
    }

    fun bindImageSlide(imageUrl: String?) {
        if (imageUrl != null) {
            Slider.getImageLoadingService().loadImage(imageUrl, imageView)
        }
    }

    fun bindImageSlide(@DrawableRes imageResourceId: Int) {
        Slider.getImageLoadingService().loadImage(imageResourceId, imageView)
    }

    fun bindImageSlide(url: String, @DrawableRes placeHolder: Int, @DrawableRes error: Int) {
        Slider.getImageLoadingService().loadImage(url, placeHolder, error, imageView)
    }

}