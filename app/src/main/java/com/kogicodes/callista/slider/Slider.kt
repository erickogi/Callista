package com.kogicodes.callista.slider

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable

import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import com.kogicodes.callista.R
import com.kogicodes.callista.slider.adapters.PositionController
import com.kogicodes.callista.slider.adapters.SliderAdapter
import com.kogicodes.callista.slider.adapters.SliderRecyclerViewAdapter
import com.kogicodes.callista.slider.event.OnSlideChangeListener
import com.kogicodes.callista.slider.event.OnSlideClickListener
import com.kogicodes.callista.slider.indicators.IndicatorShape

import java.util.Timer
import java.util.TimerTask
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class Slider : FrameLayout {

    var onSlideChangeListener: OnSlideChangeListener? = null
     var onSlideClickListenerx: OnSlideClickListener?=null
    var recyclerView: RecyclerView? = null
    var adapter: SliderRecyclerViewAdapter? = null
    var slideIndicatorsGroup: SlideIndicatorsGroup? = null
    var pendingPosition = RecyclerView.NO_POSITION
    var sliderAdapter: SliderAdapter? = null
    lateinit var config: Config
    var selectedSlidePosition = 0
    var timer: Timer? = null
    lateinit var positionController: PositionController
    private var emptyView: View? = null

    constructor(context: Context) : super(context) {
        setupViews(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupViews(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupViews(attrs)
    }

    private fun setupViews(attributeSet: AttributeSet?) {

        if (attributeSet != null) {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.Slider)
            try {
                config = Config.Builder(context)
                        .animateIndicators(typedArray.getBoolean(R.styleable.Slider_slider_animateIndicators, true))
                        .emptyView(typedArray.getResourceId(R.styleable.Slider_slider_emptyView, Config.NOT_SELECTED))
                        .indicatorSize(typedArray.getDimensionPixelSize(R.styleable.Slider_slider_indicatorSize, 0))
                        .loopSlides(typedArray.getBoolean(R.styleable.Slider_slider_loopSlides, false))
                        .slideChangeInterval(typedArray.getInteger(R.styleable.Slider_slider_interval, 0))
                        .selectedSlideIndicator(typedArray.getDrawable(R.styleable.Slider_slider_selectedSlideIndicator)!!)
                        .unselectedSlideIndicator(typedArray.getDrawable(R.styleable.Slider_slider_unselectedSlideIndicator)!!)
                        .hideIndicators(typedArray.getBoolean(R.styleable.Slider_slider_hideIndicators, false))
                        .build()
            } catch (e: Exception) {
                Log.e("Slider", "setupViews: ")
                config = Config.Builder(context).build()

            } finally {
                typedArray.recycle()
            }
        } else {
            config = Config.Builder(context).build()
        }

        setupRv()
        setupSlideIndicator()
    }

    private fun setupRv() {
        recyclerView = RecyclerView(context)
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!config.loopSlides) return

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (selectedSlidePosition == 0) {
                        recyclerView.scrollToPosition(adapter!!.itemCount - 2)
                        onImageSlideChange(adapter!!.itemCount - 2)
                    } else if (selectedSlidePosition == adapter!!.itemCount - 1) {
                        recyclerView.scrollToPosition(1)
                        onImageSlideChange(1)
                    }
                }
            }
        })
        if (config.emptyView != Config.NOT_SELECTED) {
            emptyView = LayoutInflater.from(context).inflate(config.emptyView, this, false)
            addView(emptyView)
        }
    }

    private fun setupSlideIndicator() {
        if (!config.hideIndicators) {
            slideIndicatorsGroup = SlideIndicatorsGroup(context,
                    config.selectedSlideIndicator,
                    config.unselectedSlideIndicator,
                    0,
                    config.indicatorSize,
                    config.animateIndicators)
        }
    }

    fun onImageSlideChange(position: Int) {
        Log.d(TAG, "onImageSlideChange() called with: position = [$position]")
        selectedSlidePosition = position
        val userSlidePosition = positionController.getUserSlidePosition(position)
        if (slideIndicatorsGroup != null)
            slideIndicatorsGroup!!.onSlideChange(userSlidePosition)
        if (onSlideChangeListener != null) {
            onSlideChangeListener!!.onSlideChange(userSlidePosition)
        }
    }

    fun setSelectedSlide(position: Int, animate: Boolean) {
        if (recyclerView != null && recyclerView!!.adapter != null) {
            if (animate) {
                recyclerView!!.smoothScrollToPosition(position)
            } else {
                recyclerView!!.scrollToPosition(position)
            }
            onImageSlideChange(position)
        } else {
            pendingPosition = position
        }
    }

    fun setSelectedSlide(position: Int) {
        setSelectedSlide(positionController.getRealSlidePosition(position), true)
    }

    private fun onAdapterAttached() {
        if (pendingPosition != RecyclerView.NO_POSITION) {
            recyclerView!!.smoothScrollToPosition(pendingPosition)
            onImageSlideChange(pendingPosition)
            pendingPosition = RecyclerView.NO_POSITION

        }
    }

    fun setSlideChangeListener(onSlideChangeListener: OnSlideChangeListener) {
        this.onSlideChangeListener = onSlideChangeListener
    }

    fun setOnSlideClickListener(onSlideClickListenerx: OnSlideClickListener) {
        this.onSlideClickListenerx = onSlideClickListenerx
        if (adapter != null)
            adapter!!.setOnSlideClickListener(onSlideClickListenerx)
    }

    fun getAdapter(): SliderAdapter? {
        return this.sliderAdapter
    }

    fun setAdapter(sliderAdapter: SliderAdapter?) {
        if (sliderAdapter != null && recyclerView != null) {
            this.sliderAdapter = sliderAdapter
            if (indexOfChild(recyclerView) == -1) {
                if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    recyclerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                } else {
                    recyclerView!!.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                }

                addView(recyclerView)
            }

            recyclerView!!.isNestedScrollingEnabled = false
            val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView!!.layoutManager = linearLayoutManager
            positionController = PositionController(sliderAdapter, config.loopSlides)
            adapter = SliderRecyclerViewAdapter(sliderAdapter, sliderAdapter.itemCount > 1 && config.loopSlides, recyclerView!!.layoutParams, OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    stopTimer()
                } else if (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP) {
                    startTimer()
                }
                false
            }, positionController)

            recyclerView!!.adapter = adapter
            positionController.setRecyclerViewAdapter(adapter!!)

            //Show default selected slide
            selectedSlidePosition = if (config.loopSlides) 1 else 0
            recyclerView!!.scrollToPosition(selectedSlidePosition)
            onImageSlideChange(selectedSlidePosition)
            pendingPosition = RecyclerView.NO_POSITION
            onAdapterAttached()
            val snapHelper = SsSnapHelper(object : SsSnapHelper.OnSelectedItemChange {
                override fun onSelectedItemChange(position: Int) {
                    onImageSlideChange(position)
                }
            })
           // val snapHelper = SsSnapHelper(SsSnapHelper.OnSelectedItemChange { position -> onImageSlideChange(position) })
            recyclerView!!.onFlingListener = null
            snapHelper.attachToRecyclerView(recyclerView)
            if (slideIndicatorsGroup != null && sliderAdapter.itemCount > 1) {
                if (indexOfChild(slideIndicatorsGroup) == -1) {
                    addView(slideIndicatorsGroup)
                }
                slideIndicatorsGroup!!.setSlides(sliderAdapter.itemCount)
                slideIndicatorsGroup!!.onSlideChange(0)
            }
            if (emptyView != null) {
                emptyView!!.visibility = View.GONE
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startTimer()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopTimer()
    }

    private fun startTimer() {
        if (config.slideChangeInterval > 0) {
            stopTimer()
            timer = Timer()
            timer!!.schedule(SliderTimerTask(), config.slideChangeInterval.toLong(), config.slideChangeInterval.toLong())
        }
    }

    private fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer!!.purge()
        }
    }

    private inner class SliderTimerTask : TimerTask() {
        override fun run() {
            if (context is Activity) {
                (context as Activity).runOnUiThread {
                    val nextSlidePosition = positionController.getNextSlide(selectedSlidePosition)
                    recyclerView!!.smoothScrollToPosition(nextSlidePosition)
                    onImageSlideChange(nextSlidePosition)
                }
            }
        }
    }

    fun setInterval(interval: Int) {
        config.slideChangeInterval = interval
        stopTimer()
        startTimer()
    }

    fun setLoopSlides(loopSlides: Boolean) {
        config.loopSlides = loopSlides
        adapter!!.setLoop(loopSlides)
        positionController.setLoop(loopSlides)
        adapter!!.notifyDataSetChanged()
        recyclerView!!.scrollToPosition(if (loopSlides) 1 else 0)
        onImageSlideChange(if (loopSlides) 1 else 0)
    }

    fun setAnimateIndicators(shouldAnimate: Boolean) {
        config.animateIndicators = shouldAnimate
        if (slideIndicatorsGroup != null)
            slideIndicatorsGroup!!.setMustAnimateIndicators(shouldAnimate)
    }

    fun hideIndicators() {
        config.hideIndicators = true
        if (slideIndicatorsGroup != null)
            slideIndicatorsGroup!!.visibility = View.GONE
    }

    fun showIndicators() {
        config.hideIndicators = false
        if (slideIndicatorsGroup != null)
            slideIndicatorsGroup!!.visibility = View.VISIBLE
    }

    fun setIndicatorSize(indicatorSize: Int) {
        config.indicatorSize = indicatorSize
        refreshIndicators()
    }

    fun setSelectedSlideIndicator(selectedSlideIndicator: Drawable) {
        config.selectedSlideIndicator = selectedSlideIndicator
        refreshIndicators()
    }

    fun setUnSelectedSlideIndicator(unSelectedSlideIndicator: Drawable) {
        config.unselectedSlideIndicator = unSelectedSlideIndicator
        refreshIndicators()
    }

    private fun refreshIndicators() {
        if (!config.hideIndicators && sliderAdapter != null) {
            if (slideIndicatorsGroup != null) {
                removeView(slideIndicatorsGroup)
            }
            slideIndicatorsGroup = SlideIndicatorsGroup(context, config.selectedSlideIndicator, config.unselectedSlideIndicator, 0, config.indicatorSize, config.animateIndicators)
            addView(slideIndicatorsGroup)
            for (i in 0 until sliderAdapter!!.itemCount) {
                slideIndicatorsGroup!!.onSlideAdd()
            }
        }
    }

    fun setIndicatorStyle(indicatorStyle: Int) {
        when (indicatorStyle) {
            IndicatorShape.CIRCLE -> {
                config.selectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_circle_selected)
                config.unselectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_circle_unselected)
            }
            IndicatorShape.SQUARE -> {
                config.selectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_square_selected)
                config.unselectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_square_unselected)
            }
            IndicatorShape.DASH -> {
                config.selectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_dash_selected)
                config.unselectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_dash_unselected)
            }
            IndicatorShape.ROUND_SQUARE -> {
                config.selectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_round_square_selected)
                config.unselectedSlideIndicator = ContextCompat.getDrawable(context, R.drawable.indicator_round_square_unselected)
            }
        }

        refreshIndicators()
    }

    companion object {
        private val TAG = "Slider"
        var imageLoadingServicex: ImageLoadingService? = null

        fun init(imageLoadingServicex: ImageLoadingService) {
            Slider.imageLoadingServicex = imageLoadingServicex
        }

        fun getImageLoadingService(): ImageLoadingService {
            if (imageLoadingServicex == null) {
                throw IllegalStateException("ImageLoadingService is null, you should call init method first")
            }
            return imageLoadingServicex as ImageLoadingService
        }
    }
}
