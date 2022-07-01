package com.bravedeveloper.sandbase.util.notification

sealed class NotificationAction {
    class OrderDetails(val orderNumber: Int): NotificationAction()
    object NoAction : NotificationAction()
}
