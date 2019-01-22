package com.kogicodes.callista.slider


import android.widget.ImageView

import androidx.annotation.DrawableRes


interface ImageLoadingService {
    fun loadImage(url: String, imageView: ImageView)

    fun loadImage(@DrawableRes resource: Int, imageView: ImageView)

    fun loadImage(url: String, @DrawableRes placeHolder: Int, @DrawableRes errorDrawable: Int, imageView: ImageView)
}
