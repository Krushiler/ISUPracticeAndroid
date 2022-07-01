package com.bravedeveloper.domain.model.city.order.unauthorized

import com.squareup.moshi.Json

data class OrdersUnauthorizedData(
    @Json(name = "getOrdersUnauthorized") val ordersUnauthorized: OrdersUnauthorized
)
