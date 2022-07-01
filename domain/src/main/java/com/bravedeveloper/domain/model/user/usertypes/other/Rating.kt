package com.bravedeveloper.domain.model.user.usertypes.other

import com.bravedeveloper.domain.model.user.usertypes.CustomerType
import com.bravedeveloper.domain.model.user.usertypes.SellerType

data class Rating(
    val id: Int,
    val rate: Int,
    val completed: Boolean,
    val comment: String,
    val orderId: Int,
    val from: CustomerType,
    val to: SellerType,
    val createdAt: String,
    val updatedAt: String
)