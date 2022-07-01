package com.bravedeveloper.sandbase.presentation.orderList

import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum

data class BaseOrderListSearchFilter (
    val sort: OrdersSortEnum,
    val role: UserRoleEnum,
    val searchType: OrdersSearchEnum,
    val needOwnUserId: Boolean
)