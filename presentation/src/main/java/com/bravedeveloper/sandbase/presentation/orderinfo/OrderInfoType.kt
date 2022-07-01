package com.bravedeveloper.sandbase.presentation.orderinfo

import com.bravedeveloper.domain.model.city.order.OrderStatusEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum

data class OrderInfoType(
    val orderInfoType: OrderStatusEnum,
    val userRoleEnum: UserRoleEnum,
    val hasMyResponse: Boolean,
    val hasResponses: Boolean
)
