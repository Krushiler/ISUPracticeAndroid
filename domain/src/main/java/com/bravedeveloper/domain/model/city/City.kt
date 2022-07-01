package com.bravedeveloper.domain.model.city

import com.squareup.moshi.Json

data class City (
    @Json(name="id") var id : Int,
    @Json(name="name") var name : String,
)