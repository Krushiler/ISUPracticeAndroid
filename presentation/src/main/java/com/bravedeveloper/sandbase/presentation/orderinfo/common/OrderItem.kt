package com.bravedeveloper.sandbase.presentation.orderinfo.common

import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.sandbase.presentation.orderinfo.OrderInfoType

data class OrderItem(
    val order: Order,
    val orderType: OrderInfoType
)