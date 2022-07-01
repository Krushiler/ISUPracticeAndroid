package com.bravedeveloper.sandbase.presentation.orderinfo.infoseller.orderresponses

data class MyReply(
    val name: String?,
    val price: Int?,
    val comment: String?,
    val replyId: String,
    val orderId: String
)
