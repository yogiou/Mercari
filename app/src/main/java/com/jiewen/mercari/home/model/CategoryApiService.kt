package com.jiewen.mercari.timeline.model

import com.jiewen.mercari.services.ApiFactory

class CategoryApiService {
    fun create() : CategoryApi {
        return ApiFactory.createService(CategoryApi.BASE_URL, CategoryApi::class.java)
    }
}