package com.bravedeveloper.domain.model.city.order.unauthorized

import com.bravedeveloper.domain.model.city.order.Order
import com.squareup.moshi.Json

data class OrdersUnauthorized(
    @Json(name="items") val items : List<Order>
)
