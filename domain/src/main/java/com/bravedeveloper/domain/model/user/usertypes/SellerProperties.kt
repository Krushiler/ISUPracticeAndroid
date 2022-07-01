package com.bravedeveloper.domain.model.user.usertypes

data class SellerProperties(
    val id: Int,
    val proven: Boolean,
    val seller: SellerType,
    val createdAt: String,
    val updatedAt: String
)
