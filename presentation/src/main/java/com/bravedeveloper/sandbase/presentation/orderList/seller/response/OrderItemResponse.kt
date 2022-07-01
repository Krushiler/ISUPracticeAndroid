package com.bravedeveloper.sandbase.presentation.orderList.seller.response

import com.bravedeveloper.domain.model.user.usertypes.CustomerType
import com.bravedeveloper.domain.model.user.usertypes.SellerType

data class OrderItemResponse(
    val id: String?,
    val replyId: String?,
    val title : String?,
    val location : String?,
    val dateAndTime : String?,
    val cost : String?,
    val commentaryOrder : String = "",
    val commentaryResponse : String = "",
    val number: Int?,
    val customer: CustomerType?,
    val seller: SellerType?
)