package com.bravedeveloper.sandbase.presentation.notifications

abstract class NotificationItem {
    abstract val replyId: String
    abstract val orderNumber: Int?
}