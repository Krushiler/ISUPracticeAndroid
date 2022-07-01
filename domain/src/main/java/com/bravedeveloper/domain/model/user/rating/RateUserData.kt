package com.bravedeveloper.domain.model.user.rating

import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.Rating
import com.squareup.moshi.Json

data class RateUserData(
    @Json(name = "rateId") val rateId: String
)
