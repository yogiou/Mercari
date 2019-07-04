package com.jiewen.mercari.home.model

import com.squareup.moshi.Json
import java.io.Serializable

data class CategoryData(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "data")
    val data: String
) : Serializable