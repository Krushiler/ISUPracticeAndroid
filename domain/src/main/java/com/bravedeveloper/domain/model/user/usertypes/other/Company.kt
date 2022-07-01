package com.bravedeveloper.domain.model.user.usertypes.other

import com.bravedeveloper.domain.model.user.usertypes.AdminType
import com.bravedeveloper.domain.model.user.usertypes.SellerType

data class Company(
    val id: Int,
    val name: String,
    val sellers: Array<SellerType>,
    val managers: Array<AdminType>,
    val createdAt: String,
    val type: String,
    val rating: Int
)