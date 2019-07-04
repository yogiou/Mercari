package com.jiewen.mercari.timeline.model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ProductApi {
    // Get products
    @Headers("Content-Type: application/json")
    @GET("{path}")
    fun getProducts(
        @Path("path") path: String
    ): Observable<List<ProductData>>
}