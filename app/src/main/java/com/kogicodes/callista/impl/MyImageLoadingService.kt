package com.kogicodes.callista.impl

import android.content.Context
import android.widget.ImageView


import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kogicodes.callista.R
import com.kogicodes.callista.slider.ImageLoadingService

class MyImageLoadingService(var context: Context) : ImageLoadingService {

    private val options: RequestOptions

    init {
        this.options = RequestOptions()
                .placeholder(R.drawable.imagepicker_image_placeholder)
                .error(R.drawable.imagepicker_image_placeholder)
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    }

    override fun loadImage(url: String, imageView: ImageView) {
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
    }

    override fun loadImage(resource: Int, imageView: ImageView) {
        Glide.with(context)
                .load(resource)
                .apply(options)
                .into(imageView)
    }

    override fun loadImage(url: String, placeHolder: Int, errorDrawable: Int, imageView: ImageView) {

        options.error(errorDrawable)
        options.placeholder(placeHolder)
        Glide.with(context)

                .load(url)

                .apply(options)
                .into(imageView)
    }
}
