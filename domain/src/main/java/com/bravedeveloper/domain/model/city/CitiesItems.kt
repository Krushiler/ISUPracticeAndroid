package com.bravedeveloper.domain.model.city

import com.squareup.moshi.Json

data class CitiesItems(
    @Json(name = "items") val items: List<CityName>
)
