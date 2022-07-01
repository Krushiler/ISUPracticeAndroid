package com.bravedeveloper.sandbase.presentation.orderList.seller.evaluating

data class OrderItemEvaluating (
    val id: String,
    val title : String?,
    val location : String?,
    val dateAndTime : String?,
    val commentaryOrder : String = "",
    val phone: String?,
    val number: Int?
)