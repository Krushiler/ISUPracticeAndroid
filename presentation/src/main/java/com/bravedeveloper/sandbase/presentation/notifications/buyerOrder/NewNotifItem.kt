package com.bravedeveloper.sandbase.presentation.notifications.buyerOrder

import com.bravedeveloper.sandbase.presentation.notifications.NotificationItem

data class NewNotifItem(
    override val replyId: String,
    val orderId: String,
    override val orderNumber: Int?,
    val title: String,
    val weight: String,
    val sellerName: String,
    val sellerComment: String,
    val sellerRating: Int,
    val approved: Boolean,
    val money: Boolean,
    val sertificated: Boolean,
    val price: String,
    val timeAgo: String
) : NotificationItem()