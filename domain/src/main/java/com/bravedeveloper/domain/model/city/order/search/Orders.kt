package com.bravedeveloper.domain.model.city.order.search

import com.bravedeveloper.domain.model.city.order.Order

data class Orders(
    val items: List<Order>,
    val pageInfo: PaginationInfo
)
