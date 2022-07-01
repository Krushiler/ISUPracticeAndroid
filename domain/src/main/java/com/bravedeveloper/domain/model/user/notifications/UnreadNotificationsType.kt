package com.bravedeveloper.domain.model.user.notifications

data class UnreadNotificationsType(
    val customerNotificationsCount: Int?,
    val sellerNotificationsCount: Int?
)