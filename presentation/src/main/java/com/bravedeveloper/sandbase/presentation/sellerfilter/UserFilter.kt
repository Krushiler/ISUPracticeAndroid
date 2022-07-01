package com.bravedeveloper.sandbase.presentation.sellerfilter

import com.bravedeveloper.domain.model.city.order.search.OrdersSortEnum
import com.bravedeveloper.domain.model.city.order.search.filter.OrdersSearchEnum
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum

data class UserFilter(
    var searchType: OrdersSearchEnum?,
    var sort: OrdersSortEnum?,
    var minimalValue: Int?,
    var maximalValue: Int?,
    var materials: List<String>?,
    var needOwnUserId: Boolean?,
    var role: UserRoleEnum?
)