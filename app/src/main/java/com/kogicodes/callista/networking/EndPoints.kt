package com.kogicodes.callista.networking


import com.kogicodes.callista.models.ApodModel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * @author kogi
 */
interface EndPoints {


    @GET("planetary/apod")
    fun getApodModel(@QueryMap params: Map<String, String>): Call<ApodModel>

}



