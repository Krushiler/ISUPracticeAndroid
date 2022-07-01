package com.bravedeveloper.domain.model.city.order.search

data class OrdersInput(
    val sort: OrdersSortEnum?,
    val filter: OrdersFilter?,
    val take: Int,
    val skip: Int
)
