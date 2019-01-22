package com.kogicodes.callista

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import androidx.multidex.MultiDexApplication

import com.kogicodes.callista.impl.MyImageLoadingService
import com.kogicodes.callista.slider.Slider


@SuppressLint("Registered")
class Application : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        applicationHandler = Handler(context.mainLooper)
        Slider.init( MyImageLoadingService(context))

    }

    companion object {



        lateinit var context: Context
        @Volatile
        lateinit var applicationHandler: Handler


        var application: Application? = null

    }

}
