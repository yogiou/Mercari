package com.jiewen.mercari.timeline.model

import com.jiewen.mercari.Config
import com.jiewen.mercari.home.model.CategoryData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface CategoryApi {
    companion object {

        private const val PRODUCT_PREFIX = "m-et/Android/json/"

        var BASE_URL = Config.BASE_API_URL + PRODUCT_PREFIX
    }

    // Get products data
    @Headers("Content-Type: application/json")
    @GET("master.json")
    fun getProducts(): Observable<List<CategoryData>>
}