package com.jiewen.mercari.timeline.model

import com.jiewen.mercari.services.ApiFactory

object ProductApiService {
    fun create(url: String) : ProductApi {
        return ApiFactory.createService(url, ProductApi::class.java)
    }
}