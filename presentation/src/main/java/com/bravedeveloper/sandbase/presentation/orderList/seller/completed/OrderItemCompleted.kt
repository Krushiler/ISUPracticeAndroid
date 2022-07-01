package com.bravedeveloper.sandbase.presentation.orderList.seller.completed

data class OrderItemCompleted(
    val id: String?,
    val title : String?,
    val location : String?,
    val dateAndTime : String?,
    val commentaryOrder : String = "",
    val phone: String?,
    val number: Int?
)