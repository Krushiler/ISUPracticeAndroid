package com.bravedeveloper.domain.model.city.order.getorder

import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.city.order.Rating

data class OrderDataData(
    val order: Order,
    val canRate: Boolean?,
    val rate: Rating?
)