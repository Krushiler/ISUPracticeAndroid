package com.bravedeveloper.domain.model.city.order.search

import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum

data class OrdersFilter(
    val userId: String?,
    val role: UserRoleEnum?,
    val searchType: OrdersSearchEnum?,
    val materials: List<String>?,
    val minVolume: Int?,
    val maxVolume: Int?,
    val cityIds: List<Int>?
)
