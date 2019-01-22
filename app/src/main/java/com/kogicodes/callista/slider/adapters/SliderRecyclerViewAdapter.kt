package com.kogicodes.callista.slider.adapters


import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.kogicodes.callista.slider.SlideType
import com.kogicodes.callista.slider.event.OnSlideClickListener
import com.kogicodes.callista.slider.viewholder.ImageSlideViewHolder

import androidx.recyclerview.widget.RecyclerView


class SliderRecyclerViewAdapter(private val sliderAdapter: SliderAdapter, private var loop: Boolean, private val imageViewLayoutParams: ViewGroup.LayoutParams, private val itemOnTouchListener: View.OnTouchListener, private val positionController: PositionController) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onSlideClickListener: OnSlideClickListener? = null

    fun setOnSlideClickListener(onSlideClickListener: OnSlideClickListener) {
        this.onSlideClickListener = onSlideClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       // if (viewType == SlideType.IMAGE.value) {
            val imageView = ImageView(parent.context)
            imageView.layoutParams = imageViewLayoutParams
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            return ImageSlideViewHolder(imageView)
//        } else {
//            return  null
//           // return ImageSlideViewHolder()
//
//        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!loop) {
            sliderAdapter.onBindImageSlide(position, holder as ImageSlideViewHolder)
        } else {
            if (position == 0) {
                sliderAdapter.onBindImageSlide(positionController.lastUserSlidePosition, holder as ImageSlideViewHolder)
            } else if (position == itemCount - 1) {
                sliderAdapter.onBindImageSlide(positionController.firstUserSlidePosition, holder as ImageSlideViewHolder)
            } else {
                sliderAdapter.onBindImageSlide(position - 1, holder as ImageSlideViewHolder)
            }
        }

        holder.itemView.setOnClickListener {
            if (onSlideClickListener != null)
                onSlideClickListener!!.onSlideClick(positionController.getUserSlidePosition(holder.getAdapterPosition()))
        }

        holder.itemView.setOnTouchListener(itemOnTouchListener)
    }

    override fun getItemCount(): Int {
        return sliderAdapter.itemCount + if (loop) 2 else 0
    }

    fun setLoop(loop: Boolean) {
        this.loop = loop
    }


}
