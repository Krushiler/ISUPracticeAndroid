package com.bravedeveloper.sandbase.presentation.orderList.unauthtorized

data class OrderUnauthorizedItem(
    val id: String,
    val title: String?,
    val destination: String?,
    val dateAndTime: String?,
    val commentary: String?
)