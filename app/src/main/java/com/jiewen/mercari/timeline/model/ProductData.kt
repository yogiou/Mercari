package com.jiewen.mercari.timeline.model

import com.squareup.moshi.Json
import java.io.Serializable

data class ProductData(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "num_likes")
    val num_likes: Int,
    @field:Json(name = "num_comments")
    val num_comments: Int,
    @field:Json(name = "price")
    val price: Float,
    @field:Json(name = "photo")
    val photo: String
    ) : Serializable

enum class ProductStatusEnum(val status: String) {
    SOLD_OUT("sold_out"),
    ON_SALE("on_sale"),
    NONE("");

    companion object {
        fun fromValue(value: String): ProductStatusEnum {
            return when (value) {
                "sold_out" -> SOLD_OUT
                "on_sale" -> ON_SALE
                else -> NONE
            }
        }
    }
}