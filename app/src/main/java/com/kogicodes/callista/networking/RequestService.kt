package com.kogicodes.callista.networking

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kogi
 */
object RequestService {



    private val NASA_BASE_URL="https://api.nasa.gov/"
    private val SPACEX_BASE_URL="https://api.spacexdata.com/v3/"

    private val SPACEX=1;
    private val  NASA=2;


    private//.client(getClient(token))
    val retrofit: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(getBaseUrl(0))

                .addConverterFactory(GsonConverterFactory.create())
                .build()

    val service: EndPoints
        get() = retrofit.create(EndPoints::class.java)

    private fun getRetrofit(token: String,which: Int): Retrofit {
        return Retrofit.Builder()
                .baseUrl(getBaseUrl(which))

                .addConverterFactory(GsonConverterFactory.create())

                .client(getClient(token))
                .build()
    }

    private fun getBaseUrl(which: Int): String {
        return if(which== SPACEX){
            SPACEX_BASE_URL
        }else{
            NASA_BASE_URL
        }

    }

    private fun getClient(token: String): OkHttpClient {

        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            chain.proceed(newRequest)
        }.build()
    }


    fun getService(token: String,which: Int): EndPoints {
        return getRetrofit(token,which).create(EndPoints::class.java)

    }
}
