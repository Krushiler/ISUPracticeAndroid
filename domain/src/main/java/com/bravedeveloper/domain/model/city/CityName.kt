package com.bravedeveloper.domain.model.city

import com.squareup.moshi.Json

data class CityName(
    @Json(name = "name") val name: String
)
