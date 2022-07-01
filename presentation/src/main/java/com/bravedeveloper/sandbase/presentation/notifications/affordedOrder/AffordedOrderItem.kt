package com.bravedeveloper.sandbase.presentation.notifications.affordedOrder

import com.bravedeveloper.sandbase.presentation.notifications.NotificationItem

data class AffordedOrderItem(
    override val replyId: String,
    val orderId: String,
    override val orderNumber: Int?,
    val geolocation: String,
    val weight: String,
    val dateAndTime: String,
    val buyerName: String,
    val buyerComment: String,
    val orderComment: String,
    val price: String,
    val phone: String,
    val timeAgo: String
) : NotificationItem()
