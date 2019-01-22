package com.kogicodes.callista.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ApodModel {

    @SerializedName("date")
    @Expose
    private var date: String? = null
    @SerializedName("explanation")
    @Expose
    private var explanation: String? = null
    @SerializedName("hdurl")
    @Expose
    private var hdurl: String? = null
    @SerializedName("media_type")
    @Expose
    private var mediaType: String? = null
    @SerializedName("service_version")
    @Expose
    private var serviceVersion: String? = null
    @SerializedName("title")
    @Expose
    private var title: String? = null
    @SerializedName("url")
    @Expose
    private var url: String? = null

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun getExplanation(): String? {
        return explanation
    }

    fun setExplanation(explanation: String) {
        this.explanation = explanation
    }

    fun getHdurl(): String? {
        return hdurl
    }

    fun setHdurl(hdurl: String) {
        this.hdurl = hdurl
    }

    fun getMediaType(): String? {
        return mediaType
    }

    fun setMediaType(mediaType: String) {
        this.mediaType = mediaType
    }

    fun getServiceVersion(): String? {
        return serviceVersion
    }

    fun setServiceVersion(serviceVersion: String) {
        this.serviceVersion = serviceVersion
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }
}