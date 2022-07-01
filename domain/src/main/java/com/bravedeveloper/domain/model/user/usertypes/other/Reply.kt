package com.bravedeveloper.domain.model.user.usertypes.other

import com.bravedeveloper.domain.model.city.order.Order
import com.bravedeveloper.domain.model.user.usertypes.SellerType
import com.bravedeveloper.domain.model.user.usertypes.UserRoleEnum

data class Reply(
    val id: String?,
    val price: Int?,
    val comment: String?,
    val read: List<UserRoleEnum>?,
    val approved: Boolean?,
    val order: Order?,
    val seller: SellerType?,
    val createdAt: String?,
    val updatedAt: String?,
    val deletedAt: String?
)