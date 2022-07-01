package com.bravedeveloper.sandbase.presentation.orderList.seller.approved

data class OrderItemApproved(
    val id: String?,
    val title : String?,
    val location : String?,
    val dateAndTime : String?,
    val cost : String?,
    val commentaryOrder : String = "",
    val commentaryResponse : String = "",
    val phone: String?,
    val number: Int?
)