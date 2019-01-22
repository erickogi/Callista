package com.kogicodes.callista

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.kogicodes.callista.R
import kotlinx.android.synthetic.main.activity_main.*

import com.kogicodes.callista.impl.MainSliderAdapter
import com.kogicodes.callista.slider.indicators.IndicatorShape


class Main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->

        }


        collapsingAB_layout.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
        collapsingAB_layout.setContentScrimColor(resources.getColor(R.color.colorPrimary))
        collapsingAB_layout.setStatusBarScrimColor(resources.getColor(R.color.colorPrimary))


        banner_slider1.setAdapter(MainSliderAdapter())
        banner_slider1.setLoopSlides(true)
        banner_slider1.setInterval(1000)

        banner_slider1.showIndicators()
        banner_slider1.setAnimateIndicators(true)


        banner_slider1.setIndicatorStyle(IndicatorShape.CIRCLE)


    }
    fun resideShow(view: View){

    }

}
