package com.bravedeveloper.domain.model.user.rating

import com.squareup.moshi.Json

data class RateInput(
    @Json(name="rate") val rate: Int?,
    @Json(name="completed") val completed: Boolean?,
    @Json(name="comment") val comment: String?,
    @Json(name="userId") val userId: String?,
    @Json(name="orderId") val orderId: String?
)
