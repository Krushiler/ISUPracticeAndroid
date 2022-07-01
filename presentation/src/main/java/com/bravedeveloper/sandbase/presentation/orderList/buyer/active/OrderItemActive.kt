package com.bravedeveloper.sandbase.presentation.orderList.buyer.active

data class OrderItemActive(
    val id: String,
    val title: String,
    val location: String,
    val dateAndTime: String,
    val avatar: String?,
    val commentary: String?,
    val viewCount: String,
    val countCommentary: String,
    val number: Int?
)