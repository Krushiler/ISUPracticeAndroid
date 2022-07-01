package com.bravedeveloper.domain.model.city

import com.bravedeveloper.domain.model.city.order.search.PaginationInfo
import com.squareup.moshi.Json

data class CitiesPagination(
    @Json(name = "cities") val cities: CitiesItems
)
