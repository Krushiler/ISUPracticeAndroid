package com.bravedeveloper.domain.model.city.order

import com.bravedeveloper.domain.model.user.Me
import java.util.*

data class Rating(
    val id: String,
    val rate: Int,
    val completed: Boolean,
    val comment: String?,
    val orderId: String,
    val from: Me,
    val to: Me,
    val createdAt: String,
    val updatedAt: String
)
