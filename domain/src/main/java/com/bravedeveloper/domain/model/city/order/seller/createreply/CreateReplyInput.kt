package com.bravedeveloper.domain.model.city.order.seller.createreply

data class CreateReplyInput(
    val orderId: String,
    val price: String,
    val comment: String
)