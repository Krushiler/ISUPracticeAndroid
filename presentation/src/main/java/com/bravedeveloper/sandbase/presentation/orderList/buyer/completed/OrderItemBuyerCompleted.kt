package com.bravedeveloper.sandbase.presentation.orderList.buyer.completed

data class OrderItemBuyerCompleted(
    val id: String,
    val title: String,
    val location: String,
    val dateAndTime: String,
    val avatar: String?,
    val commentary: String = "",
    val viewCount: String,
    val countCommentary: String,
    val price: String,
    val number: Int?
)